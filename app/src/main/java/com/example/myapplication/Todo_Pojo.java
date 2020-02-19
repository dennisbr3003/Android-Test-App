package com.example.myapplication;

public class Todo_Pojo {

    private String id;
        private String completed;
        private String title;
        private String userId;

        public String getId ()
        {
            return id;
        }

        public void setId (String id)
        {
            this.id = id;
        }

        public String getCompleted ()
        {
            return completed;
        }

        public void setCompleted (String completed)
        {
            this.completed = completed;
        }

        public String getTitle ()
        {
            return title;
        }

        public void setTitle (String title)
        {
            this.title = title;
        }

        public String getUserId ()
        {
            return userId;
        }

        public void setUserId (String userId)
        {
            this.userId = userId;
        }

        @Override
        public String toString()
        {
            return "ClassPojo [id = "+id+", completed = "+completed+", title = "+title+", userId = "+userId+"]";
        }

}
