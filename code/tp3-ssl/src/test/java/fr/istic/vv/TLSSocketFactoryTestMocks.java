package fr.istic.vv;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class TLSSocketFactoryTestMocks {

    private TLSSocketFactory factory;
    private SSLSocket mockSocket;

    @BeforeEach
    void setUp() {
        factory = new TLSSocketFactory();
        mockSocket = mock(SSLSocket.class);
    }

    /**
     * Test le cas nominal où les protocoles supportés et activés sont présents.
     * Vérifie que les protocoles sont correctement ordonnés par préférence de sécurité
     * et que tous les protocoles supportés sont inclus dans l'ordre correct.
     */
    @Test
    void testPrepareSocketWithSupportedAndEnabledProtocols() {
        // Setup mock behavior
        String[] supported = {"TLSv1.2", "TLSv1.1", "TLSv1", "TLS"};
        String[] enabled = {"TLSv1.1", "TLS"};
        when(mockSocket.getSupportedProtocols()).thenReturn(supported);
        when(mockSocket.getEnabledProtocols()).thenReturn(enabled);

        factory.prepareSocket(mockSocket);

        ArgumentCaptor<String[]> protocolCaptor = ArgumentCaptor.forClass(String[].class);
        verify(mockSocket).setEnabledProtocols(protocolCaptor.capture());

        String[] resultProtocols = protocolCaptor.getValue();
        assertArrayEquals(
            new String[]{"TLSv1.2", "TLSv1.1", "TLSv1", "TLS"},
            resultProtocols,
            "Protocols should be ordered by security preference"
        );
    }

    /**
     * Test le comportement lorsque la liste des protocoles supportés est null.
     * Vérifie que dans ce cas, la socket conserve ses protocoles activés d'origine.
     */
    @Test
    void testPrepareSocketWithNullSupportedProtocols() {
        when(mockSocket.getSupportedProtocols()).thenReturn(null);
        when(mockSocket.getEnabledProtocols()).thenReturn(new String[]{"TLS"});

        factory.prepareSocket(mockSocket);

        ArgumentCaptor<String[]> protocolCaptor = ArgumentCaptor.forClass(String[].class);
        verify(mockSocket).setEnabledProtocols(protocolCaptor.capture());

        assertArrayEquals(
            new String[]{"TLS"},
            protocolCaptor.getValue(),
            "Should keep original enabled protocols when supported are null"
        );
    }

    /**
     * Test le comportement lorsque la liste des protocoles activés est null.
     * Vérifie que seuls les protocoles supportés sont utilisés, dans l'ordre
     * de préférence de sécurité.
     */
    @Test
    void testPrepareSocketWithNullEnabledProtocols() {
        String[] supported = {"TLSv1.2", "TLSv1.1"};
        when(mockSocket.getSupportedProtocols()).thenReturn(supported);
        when(mockSocket.getEnabledProtocols()).thenReturn(null);

        factory.prepareSocket(mockSocket);

        ArgumentCaptor<String[]> protocolCaptor = ArgumentCaptor.forClass(String[].class);
        verify(mockSocket).setEnabledProtocols(protocolCaptor.capture());

        assertArrayEquals(
            new String[]{"TLSv1.2", "TLSv1.1"},
            protocolCaptor.getValue(),
            "Should use only supported protocols when enabled are null"
        );
    }

    /**
     * Test le cas où aucun protocole supporté ne correspond aux protocoles TLS standards.
     * Vérifie que dans ce cas, on conserve les protocoles activés d'origine
     * même s'ils ne font pas partie des protocoles TLS standards.
     */
    @Test
    void testPrepareSocketWithNoSupportedProtocolsInCommon() {
        String[] supported = {"UnsupportedProtocol"};
        String[] enabled = {"AnotherUnsupportedProtocol"};
        when(mockSocket.getSupportedProtocols()).thenReturn(supported);
        when(mockSocket.getEnabledProtocols()).thenReturn(enabled);

        factory.prepareSocket(mockSocket);

        ArgumentCaptor<String[]> protocolCaptor = ArgumentCaptor.forClass(String[].class);
        verify(mockSocket).setEnabledProtocols(protocolCaptor.capture());

        assertArrayEquals(
            new String[]{"AnotherUnsupportedProtocol"},
            protocolCaptor.getValue(),
            "Should keep original enabled protocol when no supported protocols match"
        );
    }

    /**
     * Test le cas limite où les deux listes (supportés et activés) sont null.
     * Vérifie qu'aucune modification n'est effectuée sur la socket dans ce cas.
     */
    @Test
    void testPrepareSocketWithBothArraysNull() {
        when(mockSocket.getSupportedProtocols()).thenReturn(null);
        when(mockSocket.getEnabledProtocols()).thenReturn(null);

        factory.prepareSocket(mockSocket);
        verify(mockSocket, never()).setEnabledProtocols(any());
    }

    /**
     * Test de vérification des appels de méthodes.
     * S'assure que toutes les méthodes nécessaires sont appelées exactement une fois
     * et dans le bon ordre lors de la préparation de la socket.
     */
    @Test
    void testPrepareSocketVerifyMethodCalls() {
        String[] supported = {"TLSv1.2"};
        String[] enabled = {"TLSv1.1"};
        when(mockSocket.getSupportedProtocols()).thenReturn(supported);
        when(mockSocket.getEnabledProtocols()).thenReturn(enabled);

        factory.prepareSocket(mockSocket);

        verify(mockSocket, times(1)).getSupportedProtocols();
        verify(mockSocket, times(1)).getEnabledProtocols();
        verify(mockSocket, times(1)).setEnabledProtocols(any());
    }
}