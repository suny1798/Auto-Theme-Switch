<div align="center">

# Auto Theme Switch

</div>

<p align="center">
  <img src="https://img.shields.io/badge/IDEA-2024.3%2B-blue" alt="IDEA Version"/>
  <img src="https://img.shields.io/github/v/release/yourusername/auto-theme-switch" alt="Release"/>
  <img src="https://img.shields.io/github/license/yourusername/auto-theme-switch" alt="License"/>
</p>

Automatically switch IntelliJ IDEA themes based on scheduled times.

## ✨ Features

- ⏰ **Scheduled Switching**: Set two different themes with specific switching times
- 🎨 **All Themes Supported**: Works with any installed IDEA theme
- ⚡ **Instant Switch**: Manually switch to any theme immediately
- 🔄 **Smart Priority**: Later time setting automatically takes precedence
- 💾 **Persistent Settings**: Your configuration is saved and restored on IDE restart

## 📥 Installation

### Method 1: From Release (Recommended)

1. Download the latest `auto-theme-switch-x.x.x.zip` from [Releases](https://github.com/yourusername/auto-theme-switch/releases)
2. Open IntelliJ IDEA
3. Go to `Settings/Preferences` → `Plugins` → ⚙️ (Settings icon) → `Install Plugin from Disk...`
4. Select the downloaded zip file
5. Restart IDE

### Method 2: Build from Source
```bash
git clone https://github.com/yourusername/auto-theme-switch.git
cd auto-theme-switch
./gradlew buildPlugin
# The plugin zip will be in build/distributions/
```

## 🚀 Usage

1. Open `Tools` → `Auto Theme Switch`
2. **For Scheduled Switching**:
    - Check "Enable scheduled switching"
    - Configure Theme 1 and its switching time
    - Configure Theme 2 and its switching time
    - Click OK
3. **For Instant Switching**:
    - Select a theme from the dropdown
    - Click "Switch Now"

## 📸 Screenshots

![Settings Dialog](screenshots/dialog.png)

## 🛠️ Requirements

- IntelliJ IDEA 2023.2 or later
- Java 17+

## 📝 How It Works

The plugin checks the current time every minute:
- When current time matches or exceeds a configured time, it switches to the corresponding theme
- If both times are exceeded, the later time's theme takes priority
- Settings persist across IDE restarts

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🐛 Bug Reports

Found a bug? Please [open an issue](https://github.com/yourusername/auto-theme-switch/issues).

## ⭐ Support

If you find this plugin helpful, please give it a star! ⭐