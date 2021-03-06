#include<stdio.h>
#include<sys/socket.h>
#include<netinet/in.h>
#include<errno.h>
#include<unistd.h>
#include<string.h>
#include<sys/types.h>
#include<arpa/inet.h>
#include<netinet/in.h>
#define _PORT_ 8889
#define _BACKLOG_ 10
int main()
{                                                                                                          
    int sock=socket(AF_INET,SOCK_STREAM,0);
    if(sock<0)
    {
        printf("socket()\n");
    }
    struct sockaddr_in server_socket;
    struct sockaddr_in socket;
    bzero(&server_socket,sizeof(server_socket));
    server_socket.sin_family=AF_INET;
    server_socket.sin_addr.s_addr=htonl(INADDR_ANY);
    server_socket.sin_port=htons(_PORT_);
    if(bind(sock,(struct sockaddr*)&server_socket,sizeof(struct sockaddr_in))<0)
    {
        printf("bind()\n");
        close(sock);
        return 1;
    }
    if(listen(sock,_BACKLOG_)<0)
    {
        printf("listen()\n");
        close(sock);
        return 2;
    }
    printf("success\n");  
     for(;;)
    {
        socklen_t len=0;
        int client_sock=accept(sock,(struct sockaddr*)&socket,&len);
        if(client_sock<0)
        {
            printf("accept()\n");
            return 3;
        }
        char buf_ip[INET_ADDRSTRLEN];
        memset(buf_ip,'\0',sizeof(buf_ip));

        inet_ntop(AF_INET,&socket.sin_addr,buf_ip,sizeof(buf_ip));

        printf("get connect\n");

        while(1)
        {
            char buf[1024];
            memset(buf,'\0',sizeof(buf));
            read(client_sock,buf,sizeof(buf));  


 printf("client:# %s\n",buf);

            printf("server:$ ");

            memset(buf,'\0',sizeof(buf));

            fgets(buf,sizeof(buf),stdin);
            buf[strlen(buf)-1]='\0';
            if(strncasecmp(buf,"quit",4)==0)
            {
                printf("quit\n");
                break;
            }
            write(client_sock,buf,strlen(buf)+1);
            printf("wait...\n");
        }
        close(client_sock);
    }
    close(sock);
    return 0;
}    
