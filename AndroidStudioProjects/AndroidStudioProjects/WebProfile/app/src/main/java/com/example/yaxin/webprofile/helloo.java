

/**
 * Chatroom Lab
 * CS 241 - Spring 2017
 */
#include <arpa/inet.h>
        #include <errno.h>
        #include <netdb.h>
        #include <pthread.h>
        #include <signal.h>
        #include <stdio.h>
        #include <stdlib.h>
        #include <string.h>
        #include <sys/socket.h>
        #include <sys/types.h>
        #include <unistd.h>
        #include "utils.h"
        #define MAX_CLIENTS 8

        void *process_client(void *p);

static volatile int endSession;
static volatile int sock_fd;

static volatile int clientsCount;
static volatile int clients[MAX_CLIENTS];

static pthread_mutex_t mutex = PTHREAD_MUTEX_INITIALIZER;
/**
 * Cleanup function called in main after `run_server` exits.
 * Server ending clean up (such as shutting down clients) should be handled
 * here.
 */
        void cleanup() {
        for(int i = 0; i <clientsCount; i++){
        if(clients[i] != -1)
        {
        shutdown(clients[i], SHUT_RDWR);
        }
        close(clients[i]);
        }
        close(sock_fd); // close windows
        // Your code here.
        }

/**
 * Signal handler for SIGINT.
 * Used to set flag to end server.
 */
        void close_server() {
        endSession = 1;
        // add any additional flags here you want.
        shutdown(sock_fd, SHUT_RDWR);
        cleanup();
        pthread_exit(NULL);
        }



        intptr_t check_available(int client_id){
        for(intptr_t i = 0; i < MAX_CLIENTS; i++){
        if(clients[i] == -1){
        clients[i] = client_id;
        return i;
        }
        }
        return -1;
        }

/**
 * Sets up a server connection.
 * Does not accept more than MAX_CLIENTS connections.  If more than MAX_CLIENTS
 * clients attempts to connects, simply shuts down
 * the new client and continues accepting.
 * Per client, a thread should be created and 'process_client' should handle
 * that client.
 * Makes use of 'endSession', 'clientsCount', 'client', and 'mutex'.
 *
 * port - port server will run on.
 *
 * If any networking call fails, the appropriate error is printed and the
 * function calls exit(1):
 *    - fprtinf to stderr for getaddrinfo
 *    - perror() for any other call
 */
        void run_server(char *port) {

        int s;
        int client_id;
        sock_fd = socket(AF_INET, SOCK_STREAM, 0);
        int optval = 1;
        setsockopt(sock_fd, SOL_SOCKET, SO_REUSEPORT, &optval, sizeof(optval));
        setsockopt(sock_fd, SOL_SOCKET, SO_REUSEADDR, &optval, sizeof(optval));

        struct addrinfo hints, *result; // FROM WIKI
        memset(&hints, 0, sizeof(struct addrinfo));
        hints.ai_family = AF_INET; // IPv4
        hints.ai_socktype = SOCK_STREAM;
        hints.ai_flags = AI_PASSIVE;
        //Passive
        s = getaddrinfo(NULL, port, &hints, &result);

        if (s != 0) {
        fprintf(stderr, "getaddrinfo: %s\n", gai_strerror(s));
        exit(1);
        }

        if (bind(sock_fd, result->ai_addr, result->ai_addrlen) != 0) {
        perror("bind()");
        exit(1);
        }
        freeaddrinfo(result);

        if (listen(sock_fd, MAX_CLIENTS) != 0) {
        perror("listen()");
        exit(1);
        }

        for(int i = 0; i < MAX_CLIENTS; i++){
        clients[i] = -1;
        }



        while(1){
        pthread_mutex_lock(&mutex);
        if(endSession){
        pthread_mutex_unlock(&mutex);
        return;
        }
        if(clientsCount >= MAX_CLIENTS){
        pthread_mutex_unlock(&mutex);
        continue;
        }
        pthread_mutex_unlock(&mutex);
        printf("Waiting for connection ...\n");
        client_id = accept(sock_fd, NULL, NULL);
        printf("Connection made: client_fd = %d\n", client_id);
        pthread_mutex_lock(&mutex);


        intptr_t id = check_available(client_id); // check avail able fd
        clientsCount++;
        printf("Currently servering %d clients\n", clientsCount);
        pthread_mutex_unlock(&mutex);

        pthread_t pool;
        if(pthread_create(&pool, NULL, process_client, (void*) id)){
        perror("pthread_create()");
        exit(1);
        }

        }

        }

/**
 * Broadcasts the message to all connected clients.
 *
 * message  - the message to send to all clients.
 * size     - length in bytes of message to send.
 */
        void write_to_clients(const char *message, size_t size) {
        pthread_mutex_lock(&mutex);
        for (int i = 0; i < MAX_CLIENTS; i++) {
        if (clients[i] != -1) {
        ssize_t retval = write_message_size(size, clients[i]);
        if (retval > 0) {
        retval = write_all_to_socket(clients[i], message, size);
        }
        if (retval == -1) {
        perror("write(): ");
        }
        }
        }
        pthread_mutex_unlock(&mutex);
        }

/**
 * Handles the reading to and writing from clients.
 *
 * p  - (void*)intptr_t index where clients[(intptr_t)p] is the file descriptor
 * for this client.
 *
 * Return value not used.
 */
        void *process_client(void *p) {
        pthread_detach(pthread_self());
        intptr_t clientId = (intptr_t)p;
        ssize_t retval = 1;
        char *buffer = NULL;

        while (retval > 0 && endSession == 0) {
        retval = get_message_size(clients[clientId]);
        if (retval > 0) {
        buffer = calloc(1, retval);
        retval = read_all_from_socket(clients[clientId], buffer, retval);
        }
        if (retval > 0)
        write_to_clients(buffer, retval);

        free(buffer);
        buffer = NULL;
        }

        printf("User %d left\n", (int)clientId);
        close(clients[clientId]);

        pthread_mutex_lock(&mutex);
        clients[clientId] = -1;
        clientsCount--;
        pthread_mutex_unlock(&mutex);

        return NULL;
        }

        int main(int argc, char **argv) {

        if (argc != 2) {
        fprintf(stderr, "./server <port>\n");
        return -1;
        }

        struct sigaction act;
        memset(&act, '\0', sizeof(act));
        act.sa_handler = close_server;
        if (sigaction(SIGINT, &act, NULL) < 0) {
        perror("sigaction");
        return 1;
        }

        signal(SIGINT, close_server);
        run_server(argv[1]);
        cleanup();
        pthread_exit(NULL);
        }

