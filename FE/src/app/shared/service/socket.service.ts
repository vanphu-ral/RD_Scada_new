import { Injectable } from '@angular/core';
import { Client, IMessage } from '@stomp/stompjs';
import SockJS from 'sockjs-client';
import { BehaviorSubject, Subject } from 'rxjs';

@Injectable({
    providedIn: 'root',
})
export class SocketService {
    private stompClient!: Client;
    private messageSource = new Subject<any>();;
    currentMessage$ = this.messageSource.asObservable();

    constructor() {
        // this.initConnection();
    }

    private initConnection() {
        // 🔗 Kết nối tới endpoint WebSocket trên BE
        this.stompClient = new Client({
            brokerURL: undefined, 
            webSocketFactory: () => new SockJS('http://localhost:8080/ws'),

            reconnectDelay: 5000,
            debug: (msg) => console.log('[STOMP]', msg),
        });

        this.stompClient.onConnect = () => {
            console.log('✅ WebSocket connected');
            this.stompClient.subscribe('/topic/public', (message: IMessage) => {
                const body = JSON.parse(message.body);
                this.messageSource.next(body);
            });
        };

        this.stompClient.onStompError = (frame) => {
            console.error('❌ STOMP error:', frame.headers['message']);
        };

        this.stompClient.activate();
    }

    sendMessage(destination: string, payload: any) {
        if (this.stompClient && this.stompClient.connected) {
            this.stompClient.publish({
                destination: destination,
                body: JSON.stringify(payload),
            });
        } else {
            console.warn('⚠️ STOMP client not connected');
        }
    }

    connect() {
        if (this.stompClient && this.stompClient.active) {
            console.log('⚡ STOMP client already connected');
            return;
        }
        this.initConnection();
    }

    disconnect() {
        if (this.stompClient) {
            this.stompClient.deactivate();
        }
    }
}
