# Base image
FROM ubuntu:22.04

# Install Java, SDK tools, and dependencies
RUN apt-get update && apt-get install -y \
    openjdk-17-jdk \
    unzip \
    wget \
    curl \
    git \
    && rm -rf /var/lib/apt/lists/*

# Set Java environment variables
ENV JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
ENV PATH=$JAVA_HOME/bin:$PATH

# Install Android SDK command-line tools
ENV ANDROID_SDK_ROOT=/sdk
RUN mkdir -p /sdk/cmdline-tools
WORKDIR /sdk/cmdline-tools
RUN curl -o sdk.zip https://dl.google.com/android/repository/commandlinetools-linux-9477386_latest.zip && \
    unzip sdk.zip && rm sdk.zip && \
    mkdir -p /sdk/cmdline-tools/latest && \
    mv cmdline-tools/* /sdk/cmdline-tools/latest/ && \
    rmdir cmdline-tools

ENV PATH=$ANDROID_SDK_ROOT/cmdline-tools/latest/bin:$PATH

# Accept licenses and install required SDK packages
RUN yes | sdkmanager --sdk_root=${ANDROID_SDK_ROOT} --licenses
RUN sdkmanager --sdk_root=${ANDROID_SDK_ROOT} \
    "platform-tools" \
    "platforms;android-33" \
    "build-tools;33.0.2"

# Set working directory for app
WORKDIR /app
COPY . .

# Ensure gradlew is executable
RUN chmod +x gradlew

# Build debug APK
RUN ./gradlew assembleDebug --no-daemon

# Default command
CMD ["./gradlew", "testDebugUnitTest", "--no-daemon"]
