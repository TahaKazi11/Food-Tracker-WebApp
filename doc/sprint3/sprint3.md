**Participants**: Rongda Yu, Mingshen Zhu, Salwa Abdul Qayyum, Joshua Fernandes, Bo Liu, ShiZhen Cui, Taha Kazi

**Team Capacity:** This sprint we decided to allocate 6-7 hours each to accommodate for the additional sixth user story. This includes the meetings we have before the demo as well as meeting for the demo.

**Sprint goal**: Last sprint we implemented users being able to log in but in order to increase security we want to user some kind of password encryption. We also want users to be able to search for the food they want so they can pick and choose from all the restaurants. For this, we need an available menu. We also want users to see which restaurants are in specific buildings and receive alerts everytime they go over their budget when tracking their food. We had a good idea on how to estimate every user story during Sprint 3 and did not stumble upon any spikes now.

Almost all user stories had two sub-tasks: implementing the front end to give the user a nice interface and implementing the back-end by setting up the necessary routes.

1. In order for a user to search for a food item we want them to be able to see all the options available by having a menu so to implement NOR-15 we need to implement the back-end and the front end needed for this user story which would require us to retrieve the menu data from the database and display it to the user.
2. Now that the menus are available we can implement NOR-5 so that the user can actually see the food available when typing a name. This would require narrowing down the data displayed to the user based on the keywords the user uses.
3. Since we want users to view nearby food options we had a building key in our database so that we could retrieve the restaurants and their hours of operation based on the building the user chooses and present it in the user interface to complete the user story NOR-14
4. After a user searches for food we want them to be able to add it to their tracking list to complete user story NOR-13. Hence, we must allow a user to add an item from the menu to their shopping cart. This is mainly work on the front end.
5. After a user adds food to their tracking list we want them to be reminded if they are exceeding their budget which they will set. This encompasses user story NOR-64 which once again requires to retrieve data from the database, particularly the budget stored in our Users collection in the MongoDB database and compare that against the amount they choose to spend. 
6. Also, in order to have some kind of security to this application when a user logs in we want to execute some kind of encryption to satisfy user story NOR-58. This helps our application be more secure
