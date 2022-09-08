package com.nextfeed.service.socket.session;

import org.junit.Assert;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.simp.stomp.*;
import org.springframework.messaging.support.AbstractSubscribableChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.Fail.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

//@SpringBootTest
public class SessionSocketIntegrationTest {


//    private SockJsClient sockJsClient;
//
//    private WebSocketStompClient stompClient;
////
//    private final WebSocketHttpHeaders headers = new WebSocketHttpHeaders();
////
//    @BeforeEach
//    public void setup() {
//        List<Transport> transports = new ArrayList<>();
//        transports.add(new WebSocketTransport(new StandardWebSocketClient()));
//        this.sockJsClient = new SockJsClient(transports);
//        this.stompClient = new WebSocketStompClient(sockJsClient);
//        this.stompClient.setMessageConverter(new MappingJackson2MessageConverter());
//
//    }
//
//
//
//    @Test
//    public void testRatingChange() throws InterruptedException {
//        final CountDownLatch latch = new CountDownLatch(1);
//        final AtomicReference<Throwable> failure = new AtomicReference<>();
//
//        StompSessionHandler handler = new TestSessionHandler(failure) {
//            @Override
//            public void afterConnected(final StompSession session, StompHeaders connectedHeaders) {
//                int sessionId = 1;
//                int rating = 1;
//                try {
//                    session.send("socket/socket-service/api/v1/participant/session/%d/mood/%d".formatted(sessionId, rating), "");
//                } catch (Throwable t) {
//                    failure.set(t);
//                    latch.countDown();
//                } finally {
//                    latch.countDown();
//                }
//            }
//        };
//
//        this.stompClient.connect("ws://localhost:8220/socket/session-socket/ws", this.headers, handler);
//        if (latch.await(3, SECONDS)) {
//            if (failure.get() != null) {
//                throw new AssertionError("", failure.get());
//            }
//        }
//        else {
//            fail("Greeting not received");
//        }
//    }

    @Test
    public void getGreeting() throws Exception {

//        final CountDownLatch latch = new CountDownLatch(1);
//        final AtomicReference<Throwable> failure = new AtomicReference<>();
//
//        StompSessionHandler handler = new TestSessionHandler(failure) {
//
//            @Override
//            public void afterConnected(final StompSession session, StompHeaders connectedHeaders) {
//                session.setAutoReceipt(true);
//
//                int sessionId = 1;
//                int rating = 1;
//                System.out.println("/participant/session/%d/mood/%d".formatted(sessionId, rating));
//
//                session.send("/participant/session/%d/mood/%d".formatted(sessionId, rating), "").addReceiptTask(() -> {
//                    System.out.println("??");
//                });
//
//
////                assertTrue("Hello, Spring!", "Hello, Spring!");
//
//                session.subscribe("/topic/greetings", new StompFrameHandler() {
////                    @Override
////                    public Type getPayloadType(StompHeaders headers) {
////                        return Greeting.class;
////                    }
//
//                    @Override
//                    public Type getPayloadType(StompHeaders headers) {
//                        return String.class;
//                    }
//
//                    @Override
//                    public void handleFrame(StompHeaders headers, Object payload) {
//                        try {
//                            assertEquals("Hello, Spring!", "Hello, Spring!");
//                        } catch (Throwable t) {
//                            failure.set(t);
//                        } finally {
//                            session.disconnect();
//                            latch.countDown();
//                        }
//                    }
//                });
//                try {
//                    session.send("/app/hello", "String");
//                } catch (Throwable t) {
//                    failure.set(t);
//                    latch.countDown();
//                }
//            }
//        };
//
//        this.stompClient.connect("ws://localhost:8210/api/ws", this.headers, handler);
//
//        if (latch.await(3, SECONDS)) {
//            if (failure.get() != null) {
//                throw new AssertionError("", failure.get());
//            }
//        }
//        else {
//            fail("Greeting not received");
//        }

    }

}
