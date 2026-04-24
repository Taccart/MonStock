# MonStock — Application Specifications

This file describes the specification for an Android application that manages the stock of a pantry.
It is used as input to generate code via an AI agent.

---

## 🎯 Overview

MonStock is an offline-first Android app that helps users track the contents of their pantry (and other storage locations such as fridge, cellar, garage). Users can add, edit, and remove items, get alerted before items expire, and generate shopping lists based on low or expired stock.

---

## 🏗️ Technical Stack

- **Platform**: Android (Kotlin)
- **Architecture**: MVVM + Clean Architecture (Repository pattern)
- **Local persistence**: Room database
- **Dependency injection**: Hilt
- **UI**: Jetpack Compose
- **Minimum SDK**: 26 (Android 8.0)
- **Target SDK**: latest stable
- **Language**: French UI (app name "MonStock"), English codebase
- **Sync / Cloud backup**: out of scope for Phase 1
- **Authentication**: single user, no login required for Phase 1

---

## 📐 Phase 1 — Data Model

### Pantry
- `id`: Long (primary key)
- `name`: String (e.g., "Cuisine", "Cave", "Réfrigérateur")

### Shelf
- `id`: Long (primary key)
- `pantryId`: Long (foreign key → Pantry)
- `name`: String (e.g., "Étagère haute", "Tiroir légumes")
- `capacity`: Int? (optional max number of items)

### Item
- `id`: Long (primary key)
- `shelfId`: Long (foreign key → Shelf)
- `name`: String
- `brand`: String?
- `category`: Enum (e.g., DAIRY, VEGETABLES, CANNED, BEVERAGES, FROZEN, OTHER)
- `quantity`: Double
- `unit`: Enum (e.g., PIECE, KG, LITER, CAN, BOTTLE, PACKET)
- `purchaseDate`: LocalDate?
- `expiryDate`: LocalDate?
- `minimumStockThreshold`: Double? (triggers low-stock alert when quantity falls below)
- `barcode`: String? (EAN/UPC for scanning)
- `photoUri`: String? (local URI to item photo)
- `notes`: String?

---

## 📱 Phase 2 — Screens & UX

### Home / Dashboard
- Summary cards: total items, expiring within 7 days, out-of-stock items
- Quick-access buttons: Add item, View shopping list, Scan barcode

### Pantry List Screen
- List of all pantries with item count
- Add / edit / delete a pantry

### Shelf Screen
- List of shelves inside a selected pantry
- Add / edit / delete a shelf

### Item List Screen
- Items displayed sorted by expiry date (ascending by default — soonest to expire first)
- Color coding: red (expired), orange (< 7 days), green (ok)
- Search bar (by name, brand, category)
- Filter chips (by category, shelf, expiry status)
- Sort options: expiry date, name, category, quantity

### Item Detail Screen
- Full item information display
- Edit and delete actions

### Add / Edit Item Screen
- Form with all item fields
- Barcode scanner integration (camera)
- Date pickers for purchase date and expiry date
- Photo capture / gallery picker

### Barcode Scanner Screen
- Camera-based EAN/UPC scanner
- Auto-fill item name from barcode lookup (optional, Phase 3)

### Shopping List Screen
- Auto-generated list from low-stock and out-of-stock items
- Manual item addition
- Mark items as "bought" to trigger a restock flow (auto-increment quantity)

---

## 🔔 Phase 3 — Notifications & Alerts

- **Expiry alert**: push notification X days before an item expires (configurable, default: 3 days)
- **Low stock alert**: notification when item quantity falls below `minimumStockThreshold`
- **Daily digest**: optional daily summary of expiring/low-stock items
- Notification scheduling via WorkManager

---

## 📊 Phase 4 — Statistics & Insights

- **Waste tracking**: log items thrown away before consumption
- **Consumption history**: record when items are consumed or removed
- **Most consumed items**: chart (bar or pie)
- **Cost tracking**: optional purchase price per item with monthly spend summary

---

## 📷 Phase 5 — Barcode Scanner

### Overview
Deep integration of barcode/QR code scanning to speed up item entry and stock management.

### Scanning Features
- **EAN-13 / EAN-8 / UPC-A / UPC-E** support for standard grocery products
- **QR code** support for custom internal labels
- **Batch scan mode**: scan multiple items in succession without returning to the form between each scan
- **Flashlight toggle** for scanning in low-light conditions
- **Manual barcode entry** fallback (keyboard input) when camera scan fails

### Product Database Lookup
- Query a public open product database (e.g., **Open Food Facts API**) by barcode to auto-fill:
  - Item name
  - Brand
  - Category
  - Unit
  - Photo (product image URL → cached locally)
- Display a confirmation screen before saving auto-filled data
- **Offline cache**: store previously looked-up barcodes locally in Room to avoid repeated API calls

### Custom Label Generation
- Generate and print (or share as PDF/PNG) a **QR code label** for items that have no barcode
- Label includes: item name, expiry date, quantity, shelf location

### Restock Flow via Scan
- Scan an existing item's barcode to:
  - **Increment quantity** directly from the scanner screen
  - **Update expiry date** when restocking with a new batch
  - **Move item** to a different shelf

### Settings
- Default quantity increment on restock scan (configurable)
- Enable/disable product database lookup
- Preferred barcode format filter

---

## ⚙️ Non-Functional Requirements

- **Offline-first**: all features work without internet
- **Performance**: item list must render in < 500 ms for up to 500 items
- **Accessibility**: TalkBack support, minimum contrast ratio AA
- **Dark mode**: full support
- **Data export/import**: JSON backup and restore
- **Home screen widget**: shows the top 5 items expiring soonest
- **Localization**: French (primary), English (secondary)
