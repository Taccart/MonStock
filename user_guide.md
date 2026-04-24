# MonStock — User Guide 📦

Welcome to **MonStock**, your personal pantry and stock management app. This guide walks you through everything you need to manage your food storage efficiently.

---

## Table of Contents

1. [Getting Started](#getting-started)
2. [Home Dashboard](#home-dashboard)
3. [Managing Pantries](#managing-pantries)
4. [Managing Shelves](#managing-shelves)
5. [Managing Items](#managing-items)
6. [Barcode Scanner](#barcode-scanner)
7. [Inventory Sessions](#inventory-sessions)
8. [Statistics & Insights](#statistics--insights)
9. [Notifications & Alerts](#notifications--alerts)
10. [Requirements](#requirements)

---

## Getting Started

1. Install the app on your Android device (Android 8.0 / API 26 or higher required).
2. Launch **MonStock** — no account or login is needed.
3. The app works fully **offline**. An internet connection is only used to look up product information via barcode.

---

## Home Dashboard

The dashboard is the first screen you see when opening the app. It gives you a quick overview of your stock status:

| Card | Description |
|------|-------------|
| **Total items** | The total number of items across all your pantries. |
| **Expiring soon** | Items expiring within the next 7 days. |
| **Out of stock** | Items that have reached zero quantity. |

**Quick-access buttons:**
- **Add item** — Jump straight to the item creation form.
- **View shopping list** — Open the list of items that need restocking.
- **Scan barcode** — Open the camera scanner to quickly find or add an item.

---

## Managing Pantries

A **pantry** is a storage location (e.g., *Kitchen*, *Fridge*, *Cellar*, *Garage*).

### Create a pantry
1. Go to the **Pantry List** screen from the navigation menu.
2. Tap the **+** button.
3. Enter a name and confirm.

### Edit or delete a pantry
- Swipe on a pantry entry, or tap it and use the **Edit** / **Delete** action.
- Deleting a pantry will also delete all its shelves and items.

---

## Managing Shelves

A **shelf** lives inside a pantry (e.g., *Top shelf*, *Vegetable drawer*).

### Create a shelf
1. Open a pantry from the Pantry List.
2. Tap the **+** button.
3. Enter a name and an optional capacity (maximum number of items).

### Edit or delete a shelf
- Tap the shelf entry and use the **Edit** / **Delete** action.

---

## Managing Items

Items represent individual products stored on a shelf.

### Add an item
1. Navigate to a shelf and tap **+**, or use the dashboard quick-access button.
2. Fill in the form:
   - **Name** *(required)*
   - **Brand** *(optional)*
   - **Category** — DAIRY, VEGETABLES, CANNED, BEVERAGES, FROZEN, OTHER
   - **Quantity** and **Unit** (piece, kg, litre, can, bottle, packet)
   - **Purchase date** and **Expiry date** — tap the field to open a date picker
   - **Minimum stock threshold** — quantity below which a low-stock alert fires
   - **Barcode** — enter manually or tap the camera icon to scan
   - **Photo** — take a picture or pick one from your gallery
   - **Notes** *(optional)*
3. Tap **Save**.

### Item list

The item list shows all items in a shelf with colour coding:

| Colour | Meaning |
|--------|---------|
| 🔴 Red | Expired |
| 🟠 Orange | Expires in less than 7 days |
| 🟢 Green | Good to go |

**Sort options:** expiry date, name, category, quantity.

**Filters:** category chip, shelf chip, expiry status chip.

**Search:** type in the search bar to filter by name, brand, or category.

### Item detail
Tap an item to see all its details. From the detail screen you can:
- **Edit** — modify any field.
- **Delete** — permanently remove the item.

---

## Barcode Scanner

The barcode scanner lets you add items quickly or manage existing stock by scanning EAN/UPC barcodes and QR codes.

### Scanning an item
1. Tap **Scan barcode** from the dashboard or the **+** button on the Add Item form.
2. Point the camera at the barcode. The scanner supports EAN-13, EAN-8, UPC-A, UPC-E, and QR codes.
3. If the barcode is found in the **Open Food Facts** database, a confirmation screen appears pre-filled with the product name, brand, category, and photo. Confirm or adjust the data before saving.
4. Previously looked-up barcodes are cached locally — no internet needed for repeat scans.

### Tips
- Tap the **flashlight** icon if the lighting is poor.
- If the camera cannot read the barcode, tap **Enter manually** to type the code.
- Enable **Batch scan mode** to scan multiple items in a row without returning to the form each time.

### Restock via scan
Scan an item's existing barcode to:
- **Increment quantity** directly.
- **Update the expiry date** for a new batch.
- **Move the item** to a different shelf.

### Custom QR labels
For items without a barcode, generate a QR label that encodes the item's name, expiry date, quantity, and shelf. Share or print the label as **PDF** or **PNG**.

---

## Inventory Sessions

An inventory session lets you physically count your stock and reconcile it with the app.

### Start a session
1. Open the **Inventory** section.
2. Select a **pantry** or a specific **shelf** as the scope.
3. Tap **Start inventory**.

### Counting items
- Go through each item one by one.
- For each item, confirm the quantity or enter the actual counted quantity and correct the expiry date if needed.
- A progress bar shows how many items have been checked (e.g., *12 / 34 items*).
- Use the **barcode scanner** in the session to quickly locate an item by scanning its code.

### Discrepancy report
At the end of a session, a summary shows every item where the counted quantity differs from the recorded value, including expiry date corrections and items that were never scanned (possibly missing).

### Apply corrections
Review the discrepancy report and deselect any change you do not want to apply. Tap **Apply all** to update the database in one transaction.

### Inventory history
Past completed sessions are listed with date, scope, number of items checked, and number of corrections. Tap a session to view its discrepancy report (read-only).

### Export
Export a completed session as **PDF** or **CSV** from the session detail screen.

---

## Statistics & Insights

The statistics screen gives you a view of how your pantry is used over time:

- **Waste tracking** — items thrown away before consumption.
- **Consumption history** — items consumed or removed.
- **Most consumed items** — bar or pie chart.
- **Cost tracking** — monthly spend summary if purchase prices are recorded.

---

## Notifications & Alerts

MonStock sends push notifications to keep your stock in check.

| Alert | Trigger |
|-------|---------|
| **Expiry alert** | An item is approaching its expiry date (default: 3 days before). |
| **Low stock alert** | An item's quantity falls below its minimum stock threshold. |
| **Daily digest** | Optional daily summary of expiring and low-stock items. |

Notifications are scheduled even when the app is closed, using Android's background worker system.

> **Tip:** You can configure the expiry alert lead time and enable/disable the daily digest in the app settings.

---

## Requirements

| Requirement | Minimum |
|-------------|---------|
| Android version | 8.0 (Oreo / API 26) |
| Camera | Required for barcode scanning (optional for other features) |
| Internet | Optional — used only for Open Food Facts barcode lookups |
| Storage | Used for item photos and cached product data |

---

*MonStock stores all data locally on your device. No account is required and no data is sent to any server, except optional barcode lookups to the Open Food Facts public API.*
