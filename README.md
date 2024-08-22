(This project has been developed in the simplest form and has an educational application , so it is not optimized. )

This project has the ability to ADD, DELETE, and EDIT three types of entities in the form of user, post and category.
On the main page, the last four posts can be seen in all categories, and by clicking on each category, you can access all relevant posts.
Users are categorized into , 'admin' and 'simple users' , which have different accesses that are specified using Spring Security.(Admins have the maximum access and simple users have the minimum access.)
In the admin dashboard, you can view and edit any tables related to the entities. Entering new data (such as creating a new post, user, or category) can be done.  
In the front-end of this website (which includes the public pages and an admin panel), two open source projects are used and are available through the links below:

https://github.com/frivizn/news-website-template
and
https://github.com/mazipan/bootstrap4-admin-dashboard-template

Before running the project ,connect the database using the commands written in the 'application.properties'.

After connecting the database, search for 'localhost:#the server port' in the browser. The server port can be found in the 'application.properties'.
