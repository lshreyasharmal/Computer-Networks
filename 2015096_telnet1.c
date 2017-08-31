 /*Author : Shreya Sharma (2015096) IIIT Delhi*/

 #include <sys/types.h>
 #include <sys/socket.h>
 #include <netdb.h>
 #include <stdio.h>
 #include <stdlib.h>
 #include <unistd.h>
 #include <string.h>


int buffsize = 500;
struct addrinfo hints,*res,*res_iter;
int sock;


int main(void)
{
	const char host[10],service[4];


	scanf("%s %s%*c",host,service);


	hints.ai_family = AF_UNSPEC;
	hints.ai_socktype = SOCK_STREAM;
	hints.ai_flags =0;
	hints.ai_protocol = 0;


	int s = getaddrinfo(host,service,&hints,&res);

	if(s!=0){

		printf("***not connected***\n");
		fprintf(stderr, "%s\n",gai_strerror(s) );
		return 1;
	}



	
	for(res_iter = res; NULL!=res_iter; res_iter->ai_next){

		sock = socket(res_iter->ai_family,res_iter->ai_socktype,res_iter->ai_protocol); 

		if(sock == -1)
			continue;

		int conn = connect(sock,res_iter->ai_addr,res_iter->ai_addrlen);

		if(conn!=-1){

			printf("\n\n😃   socket connected... \n");
			break;
		}

		close(sock);
	}
	
	if( !res_iter ){

		printf("%s\n","ERROR : COULD NOT CONNECTED :/" );
		return 1;
	}

	freeaddrinfo(res);





	printf("\n\nYou can now run some commands ! \n\n");


	 for(;;)
	 {
		char in[buffsize];
		
		gets(in);
		
		strcat(in,"\n\n");

		int inp_len = strlen( in );
			
		if(inp_len+1 > buffsize){

			printf("ignoring long msgs\n");
			continue;
		}

		/*sending request*/
		int w = write(sock,in,inp_len);

		if(w!= inp_len){

			printf("%s\n","ERROR : Write not Possible" );
			return 1;
		}
		char buf[buffsize];

		/*receiving request*/
		ssize_t nread = read(sock,buf,buffsize);


		if(nread==-1){

			perror("ERROR : Read Nothing");
			return 1;
		}

		/*SUCCESFUL READ*/

		printf("\n\n************************************************************\n");

		printf("Bytes Received : %ld Buffer :\n",(long)nread );
		printf("%s\n",buf );
		
		printf("************************************************************\n\n\n");

		
	}

	return 0;
}

/* SOURCES : man page of socket, man page of getaddrinfo, man page of connect */