================================================================================
starbucks_menu.csv — Data File Documentation
Starbucks App Redesign Project | CS 3443 / MVC Java Application
================================================================================

FILE OVERVIEW
--------------------------------------------------------------------------------
Filename:     starbucks_menu.csv
Format:       Comma-Separated Values (CSV), UTF-8 encoded
Total Items:  128
Last Updated: March 2026
Purpose:      Serves as the primary data source for the Starbucks menu in the
application. This file is loaded by the model layer to populate
MenuItem objects, which are then displayed through the View and
managed by the Controller.

--------------------------------------------------------------------------------
COLUMN DEFINITIONS
--------------------------------------------------------------------------------

Column 1 — item_id
Type:        Integer
Description: Unique numeric identifier for each menu item. Used as the
primary key when referencing items across the application.
Example:     1, 42, 116

Column 2 — category
Type:        String
Description: Top-level grouping of the menu item.
Values:
- "Drinks"           → All beverages served in-store
- "Food"             → All food items served in-store
- "At-Home Coffee"   → Packaged coffee products for home use

Column 3 — subcategory
Type:        String
Description: More specific grouping within the category.
Values (Drinks):
- Hot Coffee
- Cold Coffee
- Frappuccino
- Hot Tea
- Iced Tea
- Refreshers
- Hot Chocolate & Other
- Bottled & Packaged
Values (Food):
- Breakfast
- Bakery
- Cake Pops
- Lunch
- Snacks & Treats
Values (At-Home Coffee):
- Whole Bean
- VIA Instant

Column 4 — item_name
Type:        String
Description: The official display name of the menu item as it appears on the
Starbucks menu. Used as the primary label in the UI.
Example:     "Caffè Latte", "Bacon Gouda & Egg Sandwich", "VIA Instant Pike Place Roast"

Column 5 — description
Type:        String
Description: A brief description of the item including key ingredients,
flavor notes, and preparation method. Displayed in the item
detail view.
Example:     "Espresso with steamed milk and light foam"

Column 6 — size_options
Type:        String
Description: Available sizes for the item, separated by " / " for at-home
products or listed as comma-separated Starbucks size names.
Starbucks Size Reference:
- Tall   = 12 oz (hot) / 12 oz (cold)
- Grande = 16 oz
- Venti  = 20 oz (hot) / 24 oz (cold)
- Trenta = 31 oz (cold only, select items)
- Short  = 8 oz (hot only, select items)
Example:     "Tall, Grande, Venti" or "Box of 8 / 26 / 50 packets"

Column 7 — price_range_usd
Type:        String (formatted as low-high)
Description: Approximate price range in US dollars across all available
sizes. Prices may vary slightly by location. Formatted as
"X.XX-X.XX".
Example:     "4.75-5.75"

Column 8 — calories_range
Type:        Integer or String (formatted as low-high)
Description: Approximate calorie count for the item. For items with
significant calorie variation across sizes, a range is provided.
A value of 0 indicates a non-caloric item (e.g., black coffee,
water, whole bean bags).
Example:     150-250, 500, 0

Column 9 — available
Type:        String (enum)
Description: Indicates the availability status of the item.
Values:
- "Year-Round"   → Available at all times at standard locations
- "Seasonal"     → Available only during a specific season or time of year
- "Limited Time" → Short-run promotional item, not guaranteed to return

--------------------------------------------------------------------------------
DATA CATEGORIES SUMMARY
--------------------------------------------------------------------------------

  Category            | Subcategory                | Item Count
  --------------------|----------------------------|------------
  Drinks              | Hot Coffee                 | 14
  Drinks              | Cold Coffee                | 15
  Drinks              | Frappuccino                | 7
  Drinks              | Hot Tea                    | 7
  Drinks              | Iced Tea                   | 12
  Drinks              | Refreshers                 | 7
  Drinks              | Hot Chocolate & Other      | 4
  Drinks              | Bottled & Packaged         | 3
  Food                | Breakfast                  | 10
  Food                | Bakery                     | 11
  Food                | Cake Pops                  | 4
  Food                | Lunch                      | 8
  Food                | Snacks & Treats            | 5
  At-Home Coffee      | Whole Bean                 | 18
  At-Home Coffee      | VIA Instant                | 8
  --------------------|----------------------------|------------
  TOTAL               |                            | 128

--------------------------------------------------------------------------------
HOW THIS FILE IS USED IN THE APPLICATION
--------------------------------------------------------------------------------
This CSV is consumed by the Model layer of the MVC application. On startup,
the data access class reads this file and parses each row into a MenuItem object. 
The Controller then retrieves items from the model and passes the appropriate data
to the View for display.

Typical flow:
1. App launches → MenuRepository loads starbucks_menu.csv
2. User navigates to Menu screen → MenuController requests items by category
3. MenuController fetches filtered MenuItem list from MenuRepository
4. MenuController passes list to MenuView for rendering

--------------------------------------------------------------------------------
NOTES
--------------------------------------------------------------------------------
- Prices are approximate US averages and may vary by location.
- Seasonal and Limited Time items reflect the Spring 2026 menu window.
- Calorie values reflect standard preparation; customizations will vary.
- Whole Bean prices reflect standard 12oz bag retail pricing.
- VIA Instant prices reflect 8-packet box retail pricing at the low end and
  50-packet bulk boxes at the high end.
- Data sourced from Starbucks official announcements and menu listings (2026).

================================================================================
