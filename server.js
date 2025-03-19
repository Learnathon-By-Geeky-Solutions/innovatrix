require("dotenv").config();
const express = require("express");
const axios = require("axios");
const cors = require("cors");

const app = express();
const PORT = 3000;
const GOOGLE_API_KEY = process.env.GOOGLE_API_KEY; // Store API key in .env file

app.use(cors()); // Enable CORS

// Reverse Geocoding API Route
app.get("/reverse-geocode", async (req, res) => {
    try {
        const { lat, lng } = req.query;
        if (!lat || !lng) {
            return res.status(400).json({ error: "Latitude and longitude are required" });
        }

        const response = await axios.get(
            `https://maps.googleapis.com/maps/api/geocode/json?latlng=${lat},${lng}&key=${GOOGLE_API_KEY}`
        );

        res.json(response.data);
    } catch (error) {
        res.status(500).json({ error: "Error fetching location data", details: error.message });
    }
});

app.listen(PORT, () => {
    console.log(`Server running at http://localhost:${PORT}`);
});
