package com.food.fooddeliverybackend.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum CuisineType {

    INDIAN("North Indian", "South Indian", "Mughlai", "Bengali", "Punjabi"),
    CHINESE("Sichuan", "Cantonese", "Hakka", "Mandarin"),
    ITALIAN("Pizza", "Pasta", "Risotto", "Lasagna"),
    AMERICAN("Burgers", "Steaks", "Fries", "Hot Dogs"),
    MEXICAN("Tacos", "Burritos", "Quesadillas", "Enchiladas"),
    JAPANESE("Sushi", "Ramen", "Tempura", "Yakitori"),
    THAI("Pad Thai", "Green Curry", "Tom Yum", "Massaman Curry"),
    MEDITERRANEAN("Greek", "Turkish", "Lebanese", "Moroccan"),
    FRENCH("Baguette", "Croissants", "Quiche", "Coq au Vin"),
    KOREAN("Kimchi", "Bibimbap", "Korean BBQ", "Japchae"),
    VIETNAMESE("Pho", "Banh Mi", "Spring Rolls", "Bun Cha"),
    CONTINENTAL("Grilled Chicken", "Stews", "Roasts", "Pasta"),
    MIDDLE_EASTERN("Shawarma", "Falafel", "Kebabs", "Hummus"),
    AFRICAN("Ethiopian", "Nigerian", "South African", "Ghanaian"),
    SPANISH("Paella", "Tapas", "Churros", "Gazpacho"),
    SEAFOOD("Grilled Fish", "Prawns", "Lobster", "Oysters"),
    BBQ_GRILLED("Steak", "Ribs", "Brisket", "Tandoori"),
    HEALTHY_ORGANIC("Salads", "Smoothie Bowls", "Organic Wraps", "Quinoa Bowls"),
    VEGAN_VEGETARIAN("Vegan Burgers", "Tofu Dishes", "Plant-Based Curries", "Vegetable Stir-Fry"),
    FAST_FOOD("Fries", "Wraps", "Sandwiches", "Chicken Nuggets"),
    STREET_FOOD("Chaat", "Vada Pav", "Momos", "Corn on the Cob"),
    DESSERTS_BAKERY("Cakes", "Pastries", "Ice Cream", "Cookies"),
    BEVERAGES("Milkshakes", "Juices", "Bubble Tea", "Smoothies");

    private final String[] subTypes;

    CuisineType(String... subTypes) {
        this.subTypes = subTypes;
    }

    public String[] getSubTypes() {
        return subTypes;
    }

    @JsonCreator
    public static CuisineType fromString(String value) {
        return CuisineType.valueOf(value.toUpperCase());
    }

}


