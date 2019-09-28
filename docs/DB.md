
# Database Schema Definition

## em-credentials

> This Collection contain Credential and role details of the users

    {
        "_id" : ObjectId("5cdec5dac1228a0f5c548d63"),
        "un" : "<confidential>",
        "pwd" : "<confidential>",
        "pwddef" : true,
        "rl" : "user",
        "email" : "<confidential>",
        "name" : "Ruban S",
        "_class" : "com.example.emngr.mongocoll.UserCollection"
    }
    
## em-expense

> This Collection contain Expense Amount Details  

    {
        "_id" : ObjectId("5d73a74cbed76d0004894494"),
        "cat" : "FastAndFresh",
        "exp" : 275.0,
        "un" : "ajay",
        "mnth" : "9",
        "dy" : "7",
        "yr" : "2019",
        "tmstmp" : "1567860556257",
        "_class" : "com.example.emngr.mongocoll.ExpenseCollection"
    }
## em-amntblncr

> This Collection contain  Amount Balancer Details  

    {
        "_id" : ObjectId("5d754ad5e4869400046714b9"),
        "un_frm" : "ajay",
        "un_to" : "sarin",
        "amnt" : 270.0,
        "rsn" : "Food",
        "mnth" : "9",
        "dy" : "9",
        "yr" : "2019",
        "tmstmp" : "1567967957371",
        "_class" : "com.example.emngr.mongocoll.AmountBalancerCollection"
    }
## em-expense-summary

> This Collection contain  Summary of both Expense and Amount Balancer

    {
        "_id" : ObjectId("5d6b6e2d9cc21a00044ca845"),
    "un" : "vksonpdl",
    "mnth" : "9",
    "yr" : "2019",
    "e_paid" : 2434.0,
    "ab_paid" : 466.0,
    "e_ntb_paid" : -1756.75,
    "ab_ntb_paid" : -466.0,
    "_class" : "com.example.emngr.mongocoll.MonthExpenseCummaryCollection"
}  
## em-category

> This Collection contain List of Categories for Expense

    {
        "_id" : ObjectId("5d6a5430bc56e856e187e029"),
        "cat" : "FastAndFresh",
        "_class" : "com.example.emngr.mongocoll.CategoryCollection"
    }
