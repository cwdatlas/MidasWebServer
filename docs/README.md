# MidasWebServer
JavaDocs: https://cwdatlas.github.io/MidasWebServer/
Hello, Welcome to my project, MidasWebServer

First, This project centered around consuming stock data then displaying it. 
Users have personal home page that display all stock tickers they have searched.

How to use the website
1. once the application is running on your local computer, go to your favorite web browser and type in 'localhost:8080'
2. Welcome to the index page, click login at the top right of the page
3. If you have already registered, then type in your username and password, if you haven't click the 'here' text under the text boxes
4. You should be at the register page, type in your information, make sure it isn't used elsewhere and remember your password, then click the register button
5. Once you are registered, and have navigated back to the /login page, input your information. Once your logged on you should be sent to your home page
6. Welcome home, you can now type in any ticker symbol like 'AAPL' and interval 'SIXTY_MINUTES' try to use the suggested inputs that are displayed in the text boxes
7. Unce you have clicked the button bellow the text boxes, and if your information was valid, then you should be able to see the price history of the symbol you typed in.
8. You can type in any number of symbols you would like, once your done you can click the logout button in the navbar. Good bye!

How to run the website
We use a containerization system to successfully boot the website. You will pull both the mariadb and the midaswebapp images then start them as containers with the given commands

Dependencies:
Podman or Docker:
download and install podman or docker. These two container ingines have almost identical commands to run containers. I am using podman, so that is what I would recomend to have the best results. 
Here are the official instruction on how to install podman: https://podman.io/docs/installation

After podman or docker is installed, use the commands in your terminal after the podman engine is running(use the container engine's name that you are using in the command:
docker/podman pull madatlas/midasweb:0.0.1
docker/podman pull mariadb:latest

Starting the website
1. start the mariadb database by using this command in the command line: podman run --detach --name midas-mariadb --env MARIADB_DATABASE=midasweb --env MARIADB_USER=frontend --env MARIADB_PASSWORD=156545 --env MARIADB_ROOT_PASSWORD=YourPass --network non-prod -p 3306:3306 mariadb:latest
2. Use "docker/podman container list" and copy the id of the running mariadb container
3. Use "docker/podman container attach ContainerId" with the id of the container to connect to the console of the container
4. Use "ip a" to list ips of the container. copy the container's ip. we will be using it in the next command
5. Exit out of the attached container session, if you need to you can start another terminal
6. input this command into your terminal replacing the ip and other variables you wish to change: docker/podman run --detach --name midasfrontend --env DATABASE_LOCATION=//ContainerIP:3306/midasweb --env DATABASE_USERNAME=frontend --env DATABASE_PASSWORD=156545 --network non-prod -p 8080:8080 midasweb:0.0.1
7. Now go to your favorite web browser and type in "localhost:8080". You should be able to connect to the website!
8. If you want to shut down the application, docker/podman container rm ContainerID
