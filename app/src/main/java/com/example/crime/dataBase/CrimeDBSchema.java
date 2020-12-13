package com.example.crime.dataBase;

public class CrimeDBSchema {
    public static final String NAME = "crime.db";
    public static final int VERSION = 1;


    public static final class CrimeTable{
        public static final String NAME = "crime_table";

        public static final class cols{
            public static final String ID = "id";
            public static final String  UUID = "uuid";
            public static final String TITLE = "title";
            public static final String DATE = "date";
            public static final String SOLVED = "solved";
            public static final String CHECKED = "checked";
        }
    }


    public static final class UserTable{

        public static final String Name = "User_Table";

        public static final class columns{
            public static final String ID = "id";
            public static final String  UUID = "uuid";
            public static final String User_Name = "user_name";
            public static final String Pass_Word = "password";
        }

    }
}
