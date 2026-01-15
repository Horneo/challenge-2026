if (!db.getCollectionNames().includes("pos")) {
  db.createCollection("pos");
  db.createCollection("GraphPointOfSale");
}