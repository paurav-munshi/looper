package io.eventstack.looper;

import java.util.Date;

public abstract class Event<T> {
    protected String name;
    protected Date createdDate;
    protected T data;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}