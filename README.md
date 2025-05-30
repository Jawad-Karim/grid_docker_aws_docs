Docker Commands By Apna college
=====================================================
IMAGES :  
---------
List all Local images   
docker images    

Delete an image   
docker rmi <image_name>   

Remove unused images   
docker image prune    

Build an image from a Dockerfile   
docker build -t <image_name>:<version> .   //version is optional   
docker build -t <image_name>:<version> . -no-cache //build without cache   
 
CONTAINER :   
------------   
List all Local containers (running & stopped)   
docker ps -a   

List all running containers   
docker ps   

Create & run a new container   
docker run <image_name>    

//if image not available locally, it’ll be downloaded from DockerHub   
Run container in background   
docker run -d <image_name>    

Run container with custom name   
docker run - -name <container_name> <image_name>   

Port Binding in container   
docker run -p<host_port>:<container_port> <image_name>    

Set environment variables in a container   
docker run -e  <var_name>=<var_value>  <container_name>    (or <container_id)   

Start or Stop an existing container   
docker start|stop <container_name>   (or <container_id)   

Inspect a running container   
docker inspect <container_name>   (or <container_id)   

Delete a container   
docker rm <container_name>   (or <container_id)   

TROUBLESHOOT :   
--------------
Fetch logs of a container   
docker logs <container_name>   (or <container_id)   

Open shell inside running container   
docker exec -it <container_name> /bin/bash   
docker exec -it <container_name> sh   
   
DOCKER HUB :   
------------  
Pull an image from DockerHub   
docker pull <image_name>   

Publish an image to DockerHub   
docker push <username>/<image_name>   

Login into DockerHub   
docker login -u  <image_name>   
Or   
docker login      

//also, docker logout to remove credentials    
Search for an image on DockerHub   
docker search <image_name>   

VOLUMES :   
--------------   
List all Volumes   
docker volume ls   

Create new Named volume   
docker volume create <volume_name>   

Delete a Named volume   
docker volume rm <volume_name>   

Mount Named volume with running container   
docker run - -volume <volume_name>:<mount_path>   
//or using - -mount   
docker run - -mount type=volume,src=<volume_name>,dest=<mount_path>   

Mount Anonymous volume with running container   
docker run - -volume <mount_path>   

To create a Bind Mount   
docker run - -volume <host_path>:<container_path>   
//or using - -mount   
docker run - -mount type=bind,src=<host_path>,dest=<container_path>   

Remove unused local volumes   
docker volume prune   //for anonymous volumes   

NETWORK :   
------------  
List all networks   
docker network ls   

Create a network   
docker network create <network_name>   

Remove a network   
docker network rm <network_name>   

Remove all unused networks   
docker network prune  
  
********************** Selenium Grid  ***************************************************  __    
: Grid is a part of Selenium Suite  
: Selenium Grid provides running multiple tests in parallel on multiple machines with different OS, multiple browsers with different versions, and different Operating Systems.  
: It has a concept of hub and node (machine). node machines are connected to Hub machine.  
: Hub- hub is the central point where you load your tests into. The hub is launched only on a single machine,   
: Node- where execution happens. and multiple nodes can be on one machine and multiple machines with multiple nodes can be connected to the Hub.  

// Grid concept: you only run your tests on a single machine called a hub, but the execution will be done on different machines called nodes.   
: Hub and node machines can be on same/different machines with different OS.  
: Browsers, Java, Maven should be installed in node machines, no need in hub machine.  


//step by step procedures  
Step 1) Download the Selenium Server stable version into Hub & node machine from- https://www.selenium.dev/downloads/  
	: for aws/Ec2/ubuntu machine run- wget https://github.com/SeleniumHQ/selenium/releases/download/selenium-4.32.0/selenium-server-4.32.0.jar  

Step 2) You can place the selenium-server-4.32.0.jar file anywhere in your hard drive.  
Step 3) Now launch a hub using the command prompt.   
	go to Selenium Server folder open cmd.  
	type-( java -jar C:\Users\jawad\Downloads\Grid\selenium-server-4.32.0.jar hub ) and hub should successfully be launched.  

Step 4) verify whether the hub is running by using a browser.   
	http://localhost:4444/grid/console  OR  
	Machine’s IP address:4444/grid/console  example: http://192.168.1.3:4444/grid/console  
	Now hub is ready..........  
	how you get Machine's IP Address: open cmd - type ipconfig - copy the value of IPv4 address. example- 172.30.64.1  

Step 5) Now we are going to launch a node and register/connect to hub.   
	Go to Machine B/Node machine and launch another command prompt there. in my case node will be my same machine, same folder.  
	
	// if hub+node in same machine  
	type-( java -jar C:\Users\jawad\Downloads\Grid\selenium-server-4.32.0.jar node ) and node should successfully be launched.  

	// if hub+node in different machines  
	type-( java -jar C:\Users\jawad\Downloads\Grid\selenium-server-4.32.0.jar node -hub http://<hub-ip>:4444/grid/register)  
	if aws EC2 instance: first find path for selenium-server-4.32.0.jar [sudo find / -name selenium-server-4.32.0.jar] then [java -jar path node --port 5555 --http://<hub-ip>:4444/grid/register  

	// verify node is up and running -   
	( Hub Machine’s IP address:5555 OR localhost:5555 if same machine )   


// start Hub and Nodes on same machine   
note: more nodes reduce execution time in parallel test.  
 
: For Hub + one Node (stand alone mode)  
java -jar selenium-server-<version>.jar standalone  (default port is 4444)   

: For Hub (only)   
java -jar selenium-server-<version>.jar hub   (default port is 4444)  

: For Node (only)   
java -jar selenium-server-<version>.jar node   (default port is 5555)  

: Node 1  
java -jar selenium-server-<version>.jar node   (for first node no need to specify port number 5555)  

: Node 2  
java -jar selenium-server-<version>.jar node --port 6666  

// start Hub and Nodes on different machines 		| works on same machine as well  

: For Node  (ip from window/mac/linux OS machines | first set instance inbound access as all traffic)  
java -jar selenium-server-<version>.jar node --hub http://<hub-ip>:4444  

: Node 1  
java -jar selenium-server-<version>.jar node --hub http://<hub-ip>:4444  

: Node 2  
java -jar selenium-server-<version>.jar node --hub http://<hub-ip>:4444 --port 6666   

Step 6) Now execute yor test | tests will be distributed by Hub to Nodes automatically based on browser type.  

public class Grid_test {  

	WebDriver driver;  
  
	@Test  
	public void ChromeTest() throws Exception {  

		// ChromeOptions options = new ChromeOptions();  
		// FirefoxOptions options = new FirefoxOptions();  
		EdgeOptions options = new EdgeOptions();  

		options.addArguments("--headless=new");  
		
		URL hub_url = new URI("http://172.30.64.1:4444/wd/hub").toURL();  
		driver = new RemoteWebDriver(hub_url, options);  
		
		driver.manage().window().maximize();  
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));  
		driver.get("http://www.google.com");  
		System.out.println("Title : " + driver.getTitle());  
		driver.quit();  

		
	}  
		// ChromeOptions options = new ChromeOptions();  
		options.addArguments("--disable-extensions");  
		options.addArguments("--start-maximized");  
		options.setCapability("key", "value");  
		options.setCapability(null, false);  
 
		//set platform name | set platform version | get browser name  
		String platform = "OS X 10.6";  
		chromeOptions.setPlatformName(platform);  

		String version = "latest";  
		chromeOptions.setBrowserVersion(version);  

		String name = chromeOptions.getBrowserName();  
		Assertions.assertFalse(name.isEmpty(), "Browser name should not be empty");  



// For parallel Execution   
note: should not be more than thread-count="5"  
: testng.xml   
<suite name="Suite" parallel="tests" thread-count="3">  
<suite name="Suite" parallel="methods" thread-count="3">  
<suite name="Suite" parallel="classes" thread-count="5">  
   
// Open multiple nodes using .bat file  
1. open notepad - type( java -jar <.jar path> hub )- save as fileName.bat for starting hub  
2. open notepad - type( java -jar <.jar path> node --port 5555 )- save as fileName.bat for starting node 1  
3. open notepad - type( java -jar <.jar path> node --port 6666 )- save as fileName.bat for starting node 2  
4. to start hub/node double click on .bat file (first start hub file)  


***************** how to run Linux command in background ************************   
nohup <command>  (then it will allow to run more commands on same command prompt)  


**********************  DOCKER *******************************************************
------------  
what is docker, container and image ?  
: Docker is a S/W tool/platform that helps me to package my project with it's all depedencies, enviroment setup in a form of containers.  
: it helps to create, update and destroy containers.  
: It is used to create, deploy, and run applications using containers.   

why we need docker ?  
some time our automation code work in our local but dosn't in another VM, AWS VM, OS.   
in docker container we use a complete package of apps, dependencies as an image/template.   
so there mismatch doesn't happen and run the code without any issue.   

: Docker container is a single bundle/unit of a project with its all dependencies.  
: container is made in a standard way that can be run on any OS like windows, mac, linux etc.  
: Containers act as a independent virtual machine with freshly installed OS with its own software, libraries, and configurations.   
: Containers doesn't use the physical machine memory for required setup.   

: Image is a executable file or blueprint or read-only template of a container.   
: when we want to share container then we actually create an image of a container and share with team then team member can create a container using the image.  
: lets say image is an application form, when we fill-out this form with all infomation then it becomes an application. so we create a container using an image.  

: Docker Hub is central repository for Images like Github is a central repository for Projects.  

Install Docker  
-------------------  
1. google - download docker - click Docker Desktop for Windows  
2. run (Docker Desktop installer.exe) file to install Docker  
3. add a desktop docker icon  
4. check installed or not - cmd (docker --version). it will display version no. if installed.  
5. open and signup/signin Docker Account to use  

Docker ID PW   
----------------  
MShah2025  
Password1234  

Docker Terminology-  
------------------  
: Docker file - infrastructure as code  
: Image - VM snapshot  
: Container - lightweight VM created from a specific image version. we can create multiple containers from same image. its like an instance/object of a class.  
: Build - creating an image from the docker file.  
: Tag - version of image/release  
: Docker hub - Image repository (open source)  

Docker commands-  
-----------------  
Common Commands:  
 docker  run         Create and run a new container from an image  
 docker  exec        Execute a command in a running container  
 docker  ps          List containers  
 docker  build       Build an image from a Dockerfile  
 docker  pull        Download an image from a registry  
 docker  push        Upload an image to a registry  
 docker  images      List images  
 docker  login       Authenticate to a registry  
 docker  logout      Log out from a registry  
 docker  search      Search Docker Hub for images  
 docker  version     Show the Docker version information  
 docker  info        Display system-wide information  
 

: Basic commands  
-----------------  
docker --version	(shows version details)  
docker --help 		(shows all commands and other details)  
docker info 		(shows version, containers, images info)  
docker login OR docker login -u  <image_name> (to login to docker)  
docker logout  (to remove credentials)     

: Image commands  
-----------------  
docker images  (shows list of images)  
docker search <image_name> (to Search for an image on DockerHub)  
docker pull <image name> (to pull an image from docker hub)  
docker push <username>/<image_name> (Publish an image to DockerHub)  
docker rmi <image id or name> (to remove/delete an image)  
docker image prune (to Remove/delete unused images)  
docker build -t <image_name>:<version> .   //version is optional (Build an image from a Dockerfile)  
docker build -t <image_name>:<version> . -no-cache //build without cache   

: containers commands  
----------------------  
docker ps (shows list of running containers)  
docker ps -a (shows list of running containers including stopped containers)  
docker rm <container id or name> (to remove/delete a stopped container)  
docker rm -f <container_id>  (force to remove/delete a running container)  
docker kill <containerID>   (to remove/delete a running container)   
docker container prune (to remove all stopped containers)  
docker logs <container id or name> (to view the logs of a container)  
docker run <image_name>  (Create & run a new container ) //if image not available locally, it’ll be downloaded from DockerHub.  
docker run - -name <container_name> <image_name> (Run container with custom name)  
docker run -d <image_name> (Run container in background. allowing terminal to be free for other commands.)  
docker start|stop <container-id or name> (to start|stop a container)  
docker run -e  <var_name>=<var_value>  <container_name / container_id> (Set environment variables in a container)  

Remove the container:  
Use docker rm <container_id> or docker rm <container_name> to remove the container.   

If you want to force the removal, even if it's still running, use docker rm -f <container_id>.  
To remove multiple containers, list them all after docker rm, e.g., docker rm <container_id1> <container_id2>.   

 
: system commands  
-----------------  
docker stats  
docker system  
docker info        	Display system-wide information(containers are running or stopped)  
docker system df	Show docker disk usage  
docker prune		Remove unused data  
docker events      	Get real time events from the server  

: Network Commands  
-------------------  
  docker network connect <networkName>    Connect a container to a network  
  docker network create <networkName>     Create a network (default network type will be bridge)  
  docker network create <networkName> -d <network type>    Create a network (specified network type will be created)  
  docker network disconnect <networkName> Disconnect a container from a network  
  docker network inspect <networkName>    Display detailed information on one or more networks  
  docker network ls <networkName>         List networks  
  docker network prune       		  Remove all unused networks  
  docker network rm <networkName>         Remove one or more networks  


**** how to use Docker for test ****  
: to run selenium test we need to download image from docker hub then we need to create container using the images.  
: then run your tests. and tests will be run on docker platform.  

image download commands:  
--------------------------  
1) docker pull selenium/standalone-chrome:latest  or chrome:version-no  
2) docker pull selenium/standalone-firefox:latest or version-no  
3) docker pull selenium/standalone-edgde:latest or version-no  

download images:  
--------------------  
1. go to docker hub website - type selenium - then we can see lots of images created by selenium  
2. click selenium/standalone/chrome or firefox or edge - overview - Full documentation - ReadME - will take to github repo and can see complete User Guide  
3. click selenium/standalone/chrome or firefox or edge - Tags - copy the command (docker pull selenium/standalone-chrome:latest  or chrome:versionNo)  
4. open cmd/gitbash - login with userName + Password or token. type (docker login userName) then it will ask for passwod or token  
4. open cmd/gitbash - type (docker images) to see if already any images installed or not  
5. cmd/gitbash - paste the command (docker pull selenium/standalone-chrome:latest)  

* now we need to create container (commands below)  
1) create chrome container - gitbash - type (docker run -d -p 4444:4444 --shm-size="2g" selenium/standalone-chrome:latest)  
2) create firefox container - gitbash - type (docker run -d -p 4444:4444 --shm-size="2g" selenium/standalone-firefox:latest)  
3) create edge container - gitbash - type (docker run -d -p 4444:4444 --shm-size="2g" selenium/standalone-edge:latest)  

: check container is created or not- gitbash - type (docker ps) it will display container info.  

7. we need to declare our webDriver like below.....  

public class Grid_test {  

	WebDriver driver;  

	@Test  
	public void ChromeTest() throws Exception {  

		// ChromeOptions options = new ChromeOptions();  
		// FirefoxOptions options = new FirefoxOptions();  
		EdgeOptions options = new EdgeOptions();  
 
		options.addArguments("--headless=new");  
		
		URL hub_url = new URI("http://172.30.64.1:4444/wd/hub").toURL();  
		driver = new RemoteWebDriver(hub_url, options);  
		
		driver.manage().window().maximize();  
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));  
		driver.get("http://www.google.com");  
		System.out.println("Title : " + driver.getTitle());  
		driver.quit();  

		
	}  
		// ChromeOptions options = new ChromeOptions();  
		options.addArguments("--disable-extensions");  
		options.addArguments("--start-maximized");  
		options.setCapability("key", "value");  
		options.setCapability(null, false);  

		//set platform name | set platform version | get browser name  
		chromeOptions.setPlatformName(platform);  

		String version = "latest";  
		chromeOptions.setBrowserVersion(version);  

		String name = chromeOptions.getBrowserName();  
		Assertions.assertFalse(name.isEmpty(), "Browser name should not be empty");  



// For parallel Execution   
note: should not be more than thread-count="5"  
: testng.xml   
<suite name="Suite" parallel="tests" thread-count="3">  
<suite name="Suite" parallel="methods" thread-count="3">  
<suite name="Suite" parallel="classes" thread-count="5">  
  
: Run selenium test on Firefox or Edge  
1. first I need to stop chrome container,   
so, I need to get container id type in gitbash - (docker ps) then copy id and type in gitbash - (docker stop container-ID)  
2. then create Firefox or Edge container.  

*** run multiple tests in parallel using selenium Grid on Docker ***  
what is selenium Grid:   
Selenium Grid is a component of the Selenium suite that helps us to run automated tests in parallel across multiple machines and browsers.  
selenium Grid contains two components (Hub and nodes). one Hub can have multiple nodes(machine).   
one machine can have one hub + multiple nodes and one node can have multiple tests(chrome-4, firefox-4, edge-4, and ie-1).  
but the problem is if we need multiple physical machine which is costly and node machines also need all setup for selenium test like maven, testNG, dependencies, browsers etc.  
Solution is Grid with Docker.  
: Docker doesn't need to have all setup just need containers.  
: in Docker every node is a isolated container and hub node is also a container. so all nodes + hub containers can be in a single machine.  
: containers are very light weight and run very fast.  
: the easiest way to use Docker by using docker-compose.yml file. step by step below.  

*** Execution modes ***  
//note: we need to update image version from docker hub. or use version as :latest before execution   

Standalone  
----------------------------------------------------------------------------------------------  
Note: Only one Standalone container can run on port 4444 at the same time.  

: Firefox  
docker run -d -p 4444:4444 --shm-size="2g" selenium/standalone-firefox:4.32.0-20250515  

: Chrome  
docker run -d -p 4444:4444 --shm-size="2g" selenium/standalone-chrome:4.32.0-20250515  

: Edge  
docker run -d -p 4444:4444 --shm-size="2g" selenium/standalone-edge:4.32.0-20250515  

Hub and Nodes  
--------------  
The Hub and Nodes will be created on different machines/VMs, they need to know each other's IPs to communicate properly.   
If more than one node will be running on the same Machine/VM, they must be configured to expose different ports.  

: Hub - Machine/VM 1  				(execute command on gitbash for windows)  
$ docker run -d -p 4442-4444:4442-4444 --name selenium-hub selenium/hub:4.32.0-20250515  

: Node Chrome - Machine/VM 2   
macOS/Linux/Windows 	(execute command on gitbash for windows)  
$ docker run -d -p 5555:5555 \		| or -p 5556:5556 \  (use new port number if multilple node for same browser)  
    --shm-size="2g" \  
    -e SE_EVENT_BUS_HOST=172.30.64.1 \  
    -e SE_NODE_HOST=172.30.64.1 \		(ip from window/mac/linux OS machines)  
    selenium/node-chrome:4.32.0-20250515	(specify browser version for specific OS)  

Windows PowerShell  
$ docker run -d -p 5555:5555 `  
    --shm-size="2g" `  
    -e SE_EVENT_BUS_HOST=172.30.64.1 `  
    -e SE_NODE_HOST=172.30.64.1 `  
    selenium/node-chrome:4.32.0-20250515  

: Node Edge - Machine/VM 3   
macOS/Linux/Windows (execute command on gitbash for windows)  
$ docker run -d -p 5555:5555 \  
    --shm-size="2g" \  
    -e SE_EVENT_BUS_HOST=172.30.64.1 \  
    -e SE_NODE_HOST=172.30.64.1 \  
    selenium/node-edge:4.32.0-20250515  

Docker Compose  
---------------  
Docker Compose is the simplest way to start a Grid. Use the linked resources below, save them locally   
and check the execution instructions on top of each file.  

* first start Docker Desktop app *  
1) google - selenium docker - find docker-compose.yaml  # Ref: https://github.com/SeleniumHQ/docker-...  
: copy the docker-compose.yaml file content.  
: open notepad - paste - save as file name like 'docker-compose.yaml' and store into a folder.   
: go to that folder and open cmd/gitbash.  
 

2) Run 'docker-compose.yaml' file  
 docker-compose up  
 pause (add pause to see if there any problem happens. its optional)  

3) To check hub & nodes running state:    
  http://localhost:4444/grid/console  

4) To increase the number of nodes/containers   
   open another cmd/Gitbash command prompt then run the command below...   
   docker-compose scale chrome=3 		or ('chrome=total numbers of nodes' whatever I want)   

note: more nodes/containers reduce execution time in parallel testing.   

//check number of containers again   
docker ps  | or can see in http://localhost:4444/grid/console   

5) To stop the grid and cleanup the created containers.   
 docker-compose down  
or   
# To stop the execution, hit Ctrl+C   

** step by step **  
1) Create a file name like 'docker-compose.yaml'  
2) Run 'docker-compose.yaml' file  
3) To check hub & nodes running state:  
4) Increase the number of nodes/containers as needed  
5) Create testng.xml file and for parallel tests set 'parallel=classes' 'thread-count=5'  
6) Execute testng.xml file.  

** Run Docker commands using bat file **  
------------------------------------------  
in the above way we have to start/stop cmd and docker-compose file manually then we can run our testng.xml file 
but using bat file we can directly run our testng.xml file.  
1) Create a (docker_compose_start.bat) file inte the folder where docker-compose.yml file is.   
: open notepad -   

cd C:\Users\jawad\OneDrive\Documents\2.Automation_tutorials\Docker_Selenium_Grid  
docker-compose up  
pause # pause is optional  

: save as docker_compose_start.bat  

2) Create a (docker_compose_start.bat) file inte the folder where docker-compose.yml file is.   
: open notepad -   

cd C:\Users\jawad\OneDrive\Documents\2.Automation_tutorials\Docker_Selenium_Grid  
docker-compose down  
pause # pause is optional  

: save as docker_compose_stop.bat   


*** Docker network ***   
Docker neworking allows Docker containers to communicate with each other,   
with the Docker host/hub, and with external networks.  

Network Types  
--------------   
: Bridge Network  
- the default network of Docker container in single host.  
- it does automatic DNS resolution for container names.   

: Host network   
- it is helpful to isolate the host network with Docker container network.   

: Overlay Network   
- useful while connecting containers running in different hosts.  
- this network feature is mostly used in Docker Swarm and Kubrnetes.   

: Macvlan Network   
- this will assign MAC address for each and every container and makes the   
container to appear as physical device in network.  

Commands:  
-----------  
  connect     Connect a container to a network  
  create      Create a network  
  disconnect  Disconnect a container from a network  
  inspect     Display detailed information on one or more networks  
  ls          List networks  
  prune       Remove all unused networks  
  rm          Remove one or more networks  



Docker container for mySql  
---------------------------  
from Docker hub if I download mySql image and this image then it will create a container and this will have mySql database up & running.  



Docker container jenkins  
--------------------------  


************************* AWS ****************************************************************************  ** **
1. Google - aws console - Create Account - Signup - login  
2. get A machine from aws: we need a machine up & running all the time for running our Jenkins job and so on...  
- compute - EC2(Elastic Cloud Compute) - Launch instance - do name(Jenkins machine/anything) - Check Your Region(example-Ohio)  
- Amazon Machine Image(AMI- every O/S is an AMI) - select Linux ubantu (cheapest one) - t2 extra large  
- Create new key pair for login into rented machine. do key name (my key: Jenkins Machine 2025) - Create.  
- click Launch instance - initiated instance - instance ID - connect - connection type(public/private) - create/select userName - connect.
- a CloudShell command-prompt will be open. where we can write our commands.  
- now to run our tests we need to install java, maven, jenkins,   

*** Get two aws instance/machine- one for Jenkins_hub and another for node (For Grid execution).  

How to Connect to instance:  
** direct connection **  
instances - click on instance ID - connect - EC2 Instance Connect - select Connection type - select/create UserName - connect.  

** connect using SSH Client. follow instruction use cmd/Gitbash if OS is windows.  
: instances - start instance - click on instance ID - connect - SSH Client - Example - copy the command (it will connect using ssh)  
: go to downloads folder (where you downloaded your AMI key pair) - open cmd - paste SSH command - yes  

// How to start/stop/delete instance:   
: instances - select instance - instate state - start/stop/delete instance.  

// first run this command to update everything:   
sudo apt update - (to update the current packages.)  

// How to find a file directory:   
sudo find / -name jenkins.war  

// How delete a file:   
sudo rm /var/lib/jenkins  OR sudo rm -rf /var/lib/jenkins  

// find a file then delete:   
sudo find / -name jenkins.war -exec rm -rf {} \;  

// How to solve problem- "held process" while installing:   
sudo kill -9 2527 (2527 is held process no) then run install command again.   


Java on AWS machine  
--------------------------  
: check java available or not-    
java -version  

: search for a specific java version-    
sudo apt search jdk 21  

: install jdk-    
sudo apt install openjdk-21-jdk  

Maven on AWS machine ** **    
: install maven use the commands below	  
: sudo apt search maven (to see and choose a version)	   
: sudo apt install maven	  
: check_ mvn -version	  
: copy maven-home directory	  
: go to maven folder- cd maven-home directory path 	  
: type ls (we can see folders- bin boot conf lib man)	  
: go to bin folder- cd bin	  
: type ls (we can see folders- m2.conf  mvn  mvnDebug  mvnyjp)	  
: we also can copy Java_Home path from here.  


jenkins on AWS machine  
-----------------------------   
: download jenkins.war (copy jenkins war file download link)  
wget https://get.jenkins.io/war-stable/2.504.1/jenkins.war  

: check jenkins installed or not use ls (list of jar file)  
ls  

: run jenkins  
java -jar jenkins.war		|	nohup java -jar jenkins.war (to run jenkins on background)  

: stop jenkins  
sudo service jenkins stop  

: Jenkins ID (1):   
: copy and save password  
d0858ae841974fda8946435d995adc26  

: open jenkins  
aws machine IP address:8080 	(instances - click instance ID - copy IP Address)  
http://3.144.106.6:8080/  
- it will show 'This site can’t be reached'  
- go to instance - security - click Security groups - Edit inbound rules - add rule - select All traffic, Anywher-IPv4 - Save rules.  
- now try again 3.144.106.6:8080  and jenkins home page will be open.  
- Create user name and password  
User name: Mdshah  
Password4jenkins  

* jenkins setup *  

//Java: Dashboard - Manage Jenkins - Tools - add jdk - JAVA_HOME (paste the java home path)    
//Maven: Dashboard - Manage Jenkins - Tools - add maven - MAVEN_HOME (paste the maven home path)  
// check version: add build step - execute shell - type java -version or mvn -version.  
//create a job add a project from Git  
: first install Git pluggin in jenkins OR install in EC2 machine (sudo apt install git-core  
: make sure apache maven compiler in pom.xml file   
: ---------------------------------------  

	<configuration>  
 
		<source>21</source>   (java version no)  
  
  		<target>21</target>  
    
	</configuration>  
 
: ---------------------------------------  
: enter git project url and root POM (pom.xml)     
: Goals and options (clean test -Dusername=yourGitUserName -Dpassword=yourGitPassword  
: apply - save - build now.  

: Jenkins url (2):  http://18.224.24.100:8080/  
: copy and save password  
--------------------------------  
9fdc97dd10404bf884d2d0be915adf4d    
: This may also be found at: /home/ubuntu/.jenkins/secrets/initialAdminPassword  

jenkins id: admin  
Password4jenkins  

Docker with AWS  
----------------  
// install Docker on aws linux Grid machine-   
sudo apt install docker.io  

//Start and enable the Docker service-   
sudo systemctl start docker  
sudo systemctl enable docker  
OR  
sudo service docker start/stop  

// check docker- version, images, containers-   
docker --version   
sudo docker images  
sudo docker ps  


// download standalone docker images for browsers-   
google - docker hub AI - selenium/video - README - copy standalone Chrome, Firefox, Edge images then type (sudo imageLink) in cmmand-prompt   
sudo docker run -d -p 4444:4444 --shm-size="2g" selenium/standalone-chrome:4.32.0-20250505   
sudo docker run -d -p 4444:4444 --shm-size="2g" selenium/standalone-firefox:4.32.0-20250505   
sudo docker run -d -p 4444:4444 --shm-size="2g" selenium/standalone-edge:4.32.0-20250505  

// Hub and Nodes  
-----------------  
The Hub and Nodes will be created on different machines/VMs, they need to know each other's IPs to communicate properly.   
If more than one node will be running on the same Machine/VM, they must be configured to expose different ports.   

: Hub - Machine/VM 1  
$ docker run -d -p 4442-4444:4442-4444 --name selenium-hub selenium/hub:4.32.0-20250515  

: Node Chrome - Machine/VM 2  
macOS/Linux  
$ docker run -d -p 5555:5555 \		| or -p 5556:5556 \  (use new port number if multilple node for same browser)  
    --shm-size="2g" \  
    -e SE_EVENT_BUS_HOST=<ip-from-machine-1> \  
    -e SE_NODE_HOST=<ip-from-machine-2> \		(ip from window/mac/linux OS machines)  
    selenium/node-chrome:4.32.0-20250515		(specify browser version for specific OS)  

Windows PowerShell  
$ docker run -d -p 5555:5555 `  
    --shm-size="2g" `  
    -e SE_EVENT_BUS_HOST=<ip-from-machine-1> `  
    -e SE_NODE_HOST=<ip-from-machine-2> `  
    selenium/node-chrome:4.32.0-20250515  

: Node Edge - Machine/VM 3  
macOS/Linux  
$ docker run -d -p 5555:5555 \  
    --shm-size="2g" \  
    -e SE_EVENT_BUS_HOST=<ip-from-machine-1> \  
    -e SE_NODE_HOST=<ip-from-machine-3> \  
    selenium/node-edge:4.32.0-20250515  

// Docker Compose  
------------------  
Docker Compose is the simplest way to start a Grid. Use the linked resources below, save them locally 
and check the execution instructions on top of each file.   

* first start Docker Desktop app *   
1) google - selenium docker - find docker-compose.yaml  # Ref: https://github.com/SeleniumHQ/docker-...   
: copy the docker-compose.yaml file content.   
: open notepad - paste - save as file name like 'docker-compose.yaml' and store into a folder.    
: go to that folder and open cmd/gitbash.   
 

2) Run 'docker-compose.yml' file-    
 docker-compose up   
 pause (add pause to see if there any problem happens. its optional)   
 
3) To check hub & nodes running state-      
  http://localhost:4444/grid/console   

4) To increase the number of nodes/containers-    
   open another cmd/Gitbash command prompt then run the command below...    
   docker-compose scale chrome=3 		or ('chrome=total numbers of nodes' whatever I want)   

note: more nodes/containers reduce execution time in parallel testing.   
 
//check number of containers again-     
docker ps  | or can see in http://localhost:4444/grid/console   

5) To stop the grid and cleanup the created containers-    
 docker-compose down   
or     
# To stop the execution, hit Ctrl+C   

// Now I will use the docker-compose file in Linux ubuntu machine for multiple browsers images   
: google - docker hub - selenium/video - README - copy docker-compose file   
: go to terminal type- vi fileName.yml 	(vi works like notepad).    
: vi <do fine name> example- vi docker-compose.yml	(it will open text file named docker-compose.yml)   
: press i (insert mode - it will allow to write on the file.   
: paste copied docker-compose file - Esc - type (:wq) //it will save the yml file.   
: type ls 	(to see docker file created or not)   
: type cat docker-compose.yml (it will show the file content | cat stands for concatenate)   
: now install yml file - type-( sudo apt  install docker-compose )    
: type( docker-compose ) to check   

** now start/stop container **     
: To start execute this docker compose yml file run command-   
docker compose -f docker-compose.yml up  OR  sudo docker-compose up -d (add -d for detouch mode) 

: To stop execute this docker compose yml file run command-   
docker compose -f docker-compose.yml down  OR  sudo docker-compose down 


// How many docker containers per EC2 instance?   
It is common to fill an EC2 instance with 10-20 small Docker containers. 
  
commands:  
: apt is a command-line package manager and provides commands for  

Most used commands:    
  sudo apt update - update the current packages.      
  list - list packages based on package names       
  search - search in package descriptions       
  show - show package details       
  install - install packages         
  reinstall - reinstall packages         
  remove - remove packages       
  autoremove - automatically remove all unused packages       
  update - update list of available packages       
  upgrade - upgrade the system by installing/upgrading packages     
  full-upgrade - upgrade the system by removing/installing/upgrading packages    
  edit-sources - edit the source information file      
  satisfy - satisfy dependency strings    
  kill - Kill one or more running containers  







