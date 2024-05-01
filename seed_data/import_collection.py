from pymongo import MongoClient
import json

# Connection URL for the MongoDB Atlas cluster
url = 'mongodb+srv://appUser:gordon123@appcluster.aznzaz3.mongodb.net/?retryWrites=true&w=majority&appName=AppCluster'

# Database name
dbName = 'fingenius'

# Define an array of objects, each containing the collection name and JSON file path
collections_to_import = [
    {'collectionName': 'partners', 'jsonFilePath': 'fingenius.partners.json'},
    {'collectionName': 'products', 'jsonFilePath': 'fingenius.products.json'},
    {'collectionName': 'invoices', 'jsonFilePath': 'fingenius.invoices.json'},
    {'collectionName': 'transactions', 'jsonFilePath': 'fingenius.transactions.json'}
]

def import_collection(collection_name, json_file_path):
    client = MongoClient(url)

    try:
        db = client[dbName]
        collection = db[collection_name]

        # Read the JSON file for the specific collection
        with open(json_file_path, 'r') as file:
            json_content = json.load(file)

        # Insert the JSON data into the collection
        result = collection.insert_many(json_content)
        print(f"Imported {len(result.inserted_ids)} documents into {collection_name} in {dbName}")
    except Exception as e:
        print(f"Error importing data for collection {collection_name}: {e}")
    finally:
        client.close()

def import_all_collections():
    for collection_data in collections_to_import:
        import_collection(collection_data['collectionName'], collection_data['jsonFilePath'])

import_all_collections()
