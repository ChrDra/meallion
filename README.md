# meallion

meallion is a universal and scaleable online grovery ordering system.

------------------------
1. Database / Base Model
------------------------

Hibernate / JPA base model includes the following object in the orm package (object relationship model):
 
- Recipe
- Ingredient
- MealPlan
- IngredientRecipe (matching table)
- MealPlanRecipe (matching table)

- IngredientRecipePK (primary key object for matching purposes)
- MealPlanRecipePK (primary key object for matching purposes)

The object MealPlanIngredient is NOT part of the orm part. This object is temporarily generated by MealPlan.GetMealPlanIngredients().
Before creating this object, one should ideally update the ingredient amounts of a MealPlan first: UpdateIngredientAmounts(). This function merges all ingredients of all recipes in a mealplan.

Example:
MealPlan holds these 3 Recipes: "One Potato with eggs", "One Potato with butter", "One Potato with sauce".
Calling MealPlan.UpdateIngredientAmounts() will merge the hash set of ingredients together, i.e. calculating that 3 potatoes are needed.
Calling MealPlan.GetMealPlanIngredients() will then return a list of MealPlanIngredient objects, which holds one Ingredient object and a amount number.

-------------------------------------------------
2. CookBook Servlet - client-server communication
-------------------------------------------------

the CookBook servlet handles all requests to the backend. Requests contain either fulltext parameters (for GET, i.e. visible in URL) or a command parameter (for asynchronous requests):

fulltext parameters:

CookBook?recipe=[recipe_keyword]
CookBook?clear
CookBook?echo
CookBook?getingredient=[ingredient id]
CookBook?reindex
CookBook?getallsessionmealplans
CookBook?heap

command:

CookBook?command=1: request recipe search. Parameters: "time", "budget", "veggie", "vegan", "tags"
CookBook?command=2: change recipes in mealplan. Parameters: "mealplan_keyword", "recipeid", "portions"
CookBook?command=3: get mail address to get in contact. Parameters: "email_address"
CookBook?command=4: save current mealplan to database: "name", "descr"
CookBook?command=5: request menu search. Parameters: "time", "budget", "veggie", "vegan", "tags"

----------------
3. Search Engine
----------------

meallion.de runs Solr (http://meallion.de:8983/solr/meallion). 
The features.SearchEngine object can run searches based on user input on the main page. The result is a SearchResults object, which holds a list of recipes and menus found as well as a meta integer, giving information about how/if the search went well.

The method SearchEngine.IndexAll() downloads the entire database and reindexes it into Solr documents.

-------------------------------
4. Init to run on local machine
-------------------------------

1. In the resources folder, make sure the persistence.xml file contains the content of the file: persistence_FOR LOCAL USE.xml.
2. copy the meallion.conf file into the WEB-INF folder
3. Now meallion should run on local machine, connecting to meallion.de mySQL and Solr.

meallion.conf content:
-
solr_url http://meallion.de:8983/solr/meallion
solr_max_results 200
-
