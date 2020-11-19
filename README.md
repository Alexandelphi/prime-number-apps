**Please Note:** *Prime Number Server and Prime Number Proxy Service are independent applications, it's done on purpose instead of having one app with two modules.*   
`docker-compose.yml + README file placed before prime-number-server, prime-number-proxy-service folders!`

How to build and run the apps:
1) We have to build everything first.    
Please use `./build-all.sh` script (if not executable, then apply `chmod +x build-all.sh` first)   
*Note: Also, you can build each project independently by using `./gradlew clean build`*
2) Now, we can run our apps and we have two options how to do that:  
 2.1) First option is to use `docker-compose up` (in case if you have docker + docker-compose installed)  
 2.1) Second option is to use  `./gradlew run` inside each app.
Example of starting the apps:
1) `chmod +x build-all.sh && ./build-all.sh`
2) `docker-compose up` or `./gradlew run` inside each app.

Easy start: 
1) `./build-all.sh` from prime-number-app folder.
2) `docker-compose up` from prime-number-app folder


**Prime Number Server**   

This application has been built for generation prime numbers.
The main idea to create a right tool for efficient generation prime numbers and pass generated numbers to a client through gRPC.   

There are plenty methods/techniques of generating prime numbers, but not all of them are really efficient as we might think.
So, I almost immediately decided to take a look at Sieve of Eratosthenes Algorithm (optimized version) 
with really good/impressive time complexity O(sqrt(n) * log(log(n))), 
my decision is based on my experience with Algorithms / practicing LeetCode.   
I created an util singleton class where I initialize a special cache with non-prime numbers, it's gonna be created once during server startup, 
and it allows us to use the non-prime numbers cache later on many times as we wish, especially for fast generating prime numbers.
It means we can generate an array/list with needed prime numbers without any additional difficult computation, 
we just go through a loop and check a number with the non-prime numbers cache, if it's a prime or non-prime number.  
More details you can fine in `com.home.primenumber.server.util.PrimNumberUtil` class.

**Prime Number Proxy Service**   

This application has been built for connecting to gRPC server where prime number generating proceeds.
The app is using Spring Boot where REST endpoint built `GET /prime/<number>`
and gRPC client is using as a spring service.

**My thought process**   

I had many thoughts/ideas in my head, how to do everything right and etc., Also, I decided to listen to my intuition mostly.   
Let me share some thoughts:   
0) Almost immediately I realised what I have to build as I have enough experience with integrating different services between each other, I had a clear vision.
But, it turned out, I was quite naive to build a full solution without real experience of technologies, especially Scala + SBT + Finagle + Thrift together.
I was really enthusiastic, so I decided to try why not :-)   
1) Though I had almost zero experience with Finagle + Thrift and just a bit with scala, anyway I decided to try and investigate Finagle + Thrift first.  
Then quickly realised that there is no point to just implement a solution without any good understanding.
2) From mentioned technologies in the task, I heard only about `protobuf`. So, I started from searching for what Finagle + Thrift, gRPC + Protocol Buffers are.   
The most helpful was `Designing Data-Intensive Applications` book where I read the section `4. Encoding and Evolution`
because I found a really cool explanation why RPC and binary protocols were created for, and how historically everything has been changing from text formats to binary formats and so on.
3) I was learning about Finagle + Thrift and how to build apps based on it.   
4) Then I was trying to use Finagle + Thrift with Java, but after some time I faced that there are just a bit examples for Java. 
So, I had two options whether   
trying to learn Scala, SBT quickly and learn/solve some possible unpredicted issues, and in this case I could not finish the task at all,   
or   
switching to gRPC + protobuf way.   
Therefore, I decided to switch to gRPC. Yeah, I wasted my time in the wrong direction, but anyway it gave a lot of good thoughts about where Finagle suits best and so on.   
5) I was learning about gRPC + Protocol Buffers and how to build apps based on it. After some time, it became really clear that I should have used this approach from the beginning.
Anyway, I don't regret about decisions I've made. Everything what doesn't kill you, makes you smarter and stronger this world :-)   
6) Implemented simple gRPC server + client solution without REST.
7) I was digging a lot of info and investigating different solutions how to build REST over gRPC properly.
I was searching for a light-weighted solution as native as possible, with just using grpc-netty, and I found one!!!, 
but it looked quite complicated, I needed some time to understand it, so I decided to go away from this approach as I feel like it might take an enormous amount of time.
I think it would be a perfect solution to build something like grpc-gateway as a proxy-bridge between gRPC server and REST API (client side).
If I had time, I would definitely continue working on it.
8) After all said, I decided to move on and use Spring Boot for building REST client side and integrated it with gRPC client.
Also, added some error handling as it wasn't obvious what's going on if something fails.
9) Added additional bash scripts + docker-compose for fast building and running apps.
10) I had a lot of thoughts about implementing direct streaming between server and client side, and how it might suit here,
but I decided to use 'unary' calls with using blockingStub approach for now.

**Summarizing**   

Hm, I have mixed possitive feelings and a lot of thoughts.   
So, I would like to be quite short.    
I really liked the task, especially binary protocols and how it works over http/2.
I had a lot of fun and it's always cool to have an opportunity to expand your boundaries through learning new things!
Hope my solution is not gonna be totally disappointing and everything above is gonna be read :-)   
Thank you so much!!!   
