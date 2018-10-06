Here is the unified management of all  sql , the advantages are:
1 : Avoid  adding sql template files one by one in JFinalClubConfig   
2 : Exempt the  namespace from writing in the actual template file , so as not to let the  sql  definition indent back one layer.
3 : In this file, you can also  define  some general template functions through the define directive for global sharing.
   For example, defining a generic  CRUD  template function

# namespace ( "user" )
# include ( "user.sql" )
