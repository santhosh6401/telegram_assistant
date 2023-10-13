package com.telegram.personal.assistant.constant;

import lombok.Data;

@Data
public class AppConstant {
    public static final String SUCCESS = "SUCCESS";
    public static final String FAILED = "FAILED";
    public static final String TELEGRAM_BOT_NAME = "ecommerce-chat-bot";   /* on hold name */
    public static final String TELEGRAM_SEND_MESSAGE_URL = "https://api.telegram.org/bot%s/sendMessage?chat_id=%s&text=%s";
    public static final String TELEGRAM_BOT_USERNAME = "EcommerceTest123Bot";
    public static final String TELEGRAM_BOT_TOKEN = "6632614555:AAFCNgO70P9rns1TcqbafjlcaPrhbHYNgjA";
    public static final String ASSISTANT_DB_URL = "mongodb+srv://santhoshkumar061101:9788009217@testing.t7sthda.mongodb.net";
    public static final String ASSISTANT_DB_NAME = "personal_assistant";
    public static final String ASSISTANT_COLLECTION = "query_answer";

}
