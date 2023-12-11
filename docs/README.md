# MidasWebServer
JavaDocs: https://cwdatlas.github.io/MidasWebServer/
Backtrade microservice used when running app: https://github.com/cwdatlas/backtraderMicroservice
Hello, welcome to my project, MidasWebServer

First, this project is centered around consuming stock data then displaying it. 
Users have a personal home page that display all stock tickers they have searched.

How to use the website
1. once the application is running on your local computer, go to your favorite web browser and type in 'localhost:8080'
2. Welcome to the index page, click login at the top right of the page
3. As the database is ephemeral you will have to click the 'here' text under the text boxes and register an account (it is only on your database)
4. You should be at the register page, type in your information, make sure it isn't used elsewhere and remember your password, then click the register button
5. Once you are registered, and have navigated back to the /login page, input your information. Once your logged on you should be sent to your home page
6. Welcome home, you can now type in any ticker symbol like 'AAPL' and interval 'SIXTY_MINUTES' try to use the suggested inputs that are displayed in the text boxes
7. Once you have clicked the button below the text boxes, and if your information was valid, then you should be able to see the price history of the symbol you typed in.
8. You can also fill out metrics and dates in the backtrade optimized boxes. These will allow you to check the performance of metrics for certain timeframes for your preferred algorithm.
9. Once you’re done you can click the logout button in the navbar. Goodbye!

How to run the website
We use a containerization system to successfully boot the website. You will pull mariadb:latest, madatlas/midasbacktrader:1.0 and madatlas/midasweb:0.0.2 containers then start them with the given commands

Dependencies:
Podman or Docker:
download and install podman or docker. These two container engines have almost identical commands, so change the word podman to docker in my commands if you are using docker. I am using podman, so that is what I would recommend to have the best results. 
Podman:
Official Instructions for Mac and Windows: https://podman.io/docs/installation

Docker: 
Install on MAC: https://docs.docker.com/desktop/install/mac-install/
Windows: https://docs.docker.com/desktop/install/windows-install/
Linux: https://docs.docker.com/desktop/install/linux-install/

After podman or docker is installed, use the commands in your terminal after the podman engine is running(use the container engine's name that you are using in the command:
podman pull madatlas/midasbacktrader:1.0
podman pull madatlas/midasweb:0.0.2
podman pull mariadb:latest

Starting the website
1. To start maria db use this command in the command line: podman run --detach --name midas-mariadb --env MARIADB_DATABASE=midasweb --env MARIADB_USER=frontend --env MARIADB_PASSWORD=123456--env MARIADB_ROOT_PASSWORD=YourDataBase!  --network non-prod -p 3306:3306 mariadb:latest
2. To start the backtrader miroservice us this command: podman run --detach --name midas-backtrader --network non-prod -p 5000:8080 midasbacktrader:1.0
3. To start the front end service us this command: podman run --detach --name midas-frontend --env DATABASE_LOCATION=//midas-mariadb:3306/midasweb --env DATABASE_USERNAME=frontend --env DATABASE_PASSWORD=123456 --env  BACKTRADE_MICROSERVICE=//midas-backtrader:5000 --network non-prod -p 8080:8080 midasweb:0.0.2
7. Now go to your favorite web browser and type in "localhost:8080". You should be able to connect to the website!
8. If you want to shut down the application, podman container rm “the container’s name”.
