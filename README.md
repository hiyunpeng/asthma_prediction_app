# Asthma Prediction App

This is a Kotlin Multiplatform Mobile (KMM) Android app that collects user lifestyle and demographic data and predicts the likelihood of asthma using a machine learning model namely Gradient Boosted Decision Tree (GBDT).

## 💡 Features

- 📋 Interactive questionnaire with user-friendly UI
- 🔍 Summarized user input before prediction
- 🤖 Predicts asthma risk using a trained Gradient Boosting model
- 🧠 Model runs locally via ONNX integration (WIP)
- ✨ Built using Jetpack Compose & Navigation

## 🛠️ Tech Stack

- **Kotlin Multiplatform**
- **Jetpack Compose**
- **Navigation Compose**
- **ONNX Runtime (planned for model execution)**

## 🚀 Getting Started

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/asthma-prediction-app.git

2. Goes to the folder
    ```bash
   cd asthma-prediction-app
    
2. Make sure you have:
Android Studio Electric Eel or later
Kotlin Multiplatform plugin enabled
Compose UI support enabled

## 🚀 Machine Learning models
Model: Gradient Boosted Decision Tree (GBDT)
Framework: scikit-learn
Format: Saved as .pkl and converted to .onnx for Android compatibility
Inference Engine: onnxruntime


