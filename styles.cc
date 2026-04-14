/* Root styling */
.root {
    -fx-font-family: "Segoe UI";
    -fx-background-color: yellow;
}

/* Title */
.label {
    -fx-font-size: 14px;
}

.label:first-child {
    -fx-font-size: 20px;
    -fx-font-weight: bold;
}

/* Text fields */
.text-field {
    -fx-padding: 8;
    -fx-border-radius: 5;
    -fx-background-radius: 5;
}

/* Buttons */
.button {
    -fx-background-color: #0078D7;
    -fx-text-fill: white;
    -fx-font-weight: bold;
    -fx-background-radius: 6;
}

.button:hover {
    -fx-background-color: #005fa3;
}

/* Table styling */
.table-view {
    -fx-background-radius: 8;
}

/* Red text for zero quantity (we'll use this later) */
.zero-stock {
    -fx-text-fill: red;
    -fx-font-weight: bold;
}
