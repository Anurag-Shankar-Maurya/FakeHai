# FakeHai - Fake News Detection App

## Overview
**FakeHai** is an Android application developed using **Kotlin and XML** that detects whether a given news article is fake or real. The app leverages **Google's Gemini API** to perform deep analysis of news articles based on their title, content, subject, and date.

## Features
- **AI-Powered Fake News Detection**: Analyzes news articles using Gemini API.
- **User Input Fields**: Allows users to input news title, text, subject, and date.
- **Real-time Analysis**: Provides instant feedback on the credibility of news.
- **Graphical Representation**: Displays results in a visually appealing manner.
- **Modern UI**: Designed using **Material3** theme for a sleek and responsive user interface.
- **Deep Search**: Performs thorough fact-checking to verify authenticity.

## Technologies Used
- **Language**: Kotlin
- **UI Framework**: XML (Material3 UI Components)
- **API**: Google Gemini API
- **Architecture**: MVVM (Model-View-ViewModel)
- **Database**: Room (for storing past searches)
- **Dependency Injection**: Dagger/Hilt (optional)

## Installation
1. Clone this repository:
   ```sh
   git clone https://github.com/yourusername/FakeHai.git
   ```
2. Open the project in **Android Studio**.
3. Sync Gradle files and ensure dependencies are installed.
4. Obtain an API key from Google Gemini API and add it to `local.properties`:
   ```properties
   GEMINI_API_KEY=your_api_key_here
   ```
5. Build and run the application on an emulator or physical device.

## Usage
1. Open the app and enter the **news title**, **content**, **subject**, and **date**.
2. Click the **Check News** button.
3. The app will analyze the article and display whether it is **Fake or Real**.
4. View graphical representations of credibility scores.

## Folder Structure
```
FakeHai/
│-- app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/babumusai/fakehai/
│   │   │   │   ├── ui/        # UI-related files (Activities, Fragments)
│   │   │   │   ├── data/      # Data sources (API, Room DB, Repository)
│   │   │   │   ├── viewmodel/ # ViewModel for managing UI state
│   │   │   │   ├── utils/     # Utility functions and helpers
│   │   │   ├── res/
│   │   │   │   ├── layout/    # XML layouts
│   │   │   │   ├── values/    # Colors, strings, styles
│-- build.gradle
│-- local.properties
│-- README.md
```

## API Integration
The app uses the **Google Gemini API** to analyze news credibility. The request format is:
```json
{
  "title": "News Title",
  "text": "Full News Content",
  "subject": "Category of News",
  "date": "YYYY-MM-DD"
}
```
Response:
```json
{
  "status": "Fake/Real",
  "confidence": 92.5
}
```

## Future Enhancements
- **Multi-language support**.
- **Improved NLP techniques for better accuracy**.
- **Integration with fact-checking databases**.
- **User authentication for personalized history tracking**.

## Contributing
Contributions are welcome! If you'd like to contribute:
1. Fork the repository.
2. Create a feature branch: `git checkout -b feature-name`
3. Commit changes: `git commit -m 'Add new feature'`
4. Push to branch: `git push origin feature-name`
5. Submit a pull request.

## License
This project is licensed under the **MIT License**.

## Contact
For any queries, feel free to reach out at **your.email@example.com** or visit the [GitHub Repository](https://github.com/yourusername/FakeHai).

