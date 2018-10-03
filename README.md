
Welcome to the SiteCrawler page!
================================

WHAT IS IT?
-----------

It is a program for web crawling.

> WARNING! It is just a study project which was created to utilize my current java and SQL knowledge.
I am not responsible for any damage caused by the work of this program.

The program is free and you may use it's code as you wish.
> WARNING! I have no right to any external library of this project! So if you want to use the code which
depends on an external library, please check it's license agreement to make sure that you don't violate it.

WHAT IS THE AIM?
----------------
The aim is to create a user-friendly program for web crawling. The project is still under development.
Current TODO list:
- make settings more flexible: provide the user with opportunity to add seed URLs, number of crawlers,
politeness delay and so on;
- Add more tools in SiteCrawler Browser: deleting, editing, etc;
- Change DAOFactory with DI to get rid of tight coupling;
- Add support for embedded database(h2?);
- Add Russian localization;
? Consider using Spring or Hibernate to get rid of a strong inter-dependency inside the project.

HOW TO WORK?
------------
Create dao.properties file and add tomcat pool properties.

Example:

postgresql.url = jdbc:postgresql://localhost:5432/web_pages
<br/>postgresql.driver = org.postgresql.Driver
<br/>postgresql.username = root
<br/>postgresql.password = root

To make a crawl, you have to provide at least domain name and max pages to crawl values.

Example:

Domain name: https://stackoverflow.com
<br/>Max pages to crawl: 10

It means that SiteCrawler will download no more than 10 pages from this site.

If you need only specified pages you can add "Placed in" value. for instance:
Placed in: questions/
Then, your request returns no more then 10 pages which start with https://stackoverflow.com/questions/

To download particular part of a page use [jsoup selectors](https://jsoup.org/cookbook/extracting-data/selector-syntax) in the HTML element field.

WHAT ARE THE DIFFERENCES OF THE CURRENT VERSION?
------------------------------------------------
- the project moved to maven;
- Prepared for internationalization (Now, it is possible to translate the program
by editing "I18N.properties" file);
- Added loging system to make debugging easy;
- Minor fixes nad changes.