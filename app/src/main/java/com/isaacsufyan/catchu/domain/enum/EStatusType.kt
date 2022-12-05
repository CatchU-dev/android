package com.isaacsufyan.catchu.domain.enum

enum class EStatusType(status: String) {
    Active (status = "Active"),
    Initiated (status = "Initiated"),
    Terminated (status = "Terminated"),
    Completed (status = "Completed"),
    Success (status = "Success"),
    InProgress (status = "In Progress"),
    Pending (status = "Pending"),
    Failed (status = "Failed"),
    Expired (status = "Expired")
}