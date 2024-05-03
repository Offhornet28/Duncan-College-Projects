import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MyHashMap2<K, V>
{
    public class Entry<K, V>{
        private K key;
        private V value;
        private Entry<K, V> next;

        public Entry(K key, V value){
            this.key = key;
            this.value = value;
        }

        public K getKey(){
            return this.key;
        }

        public V getValue(){
            return this.value;
        }

        public void setValue(V value){
            this.value = value;
        }
    }

    private int size;
    private int numWords;
    private Entry<K, V> table[];

    public MyHashMap2(int arraySize){
        size = arraySize;
        table = new Entry[size];
    }

    public void setNumWords(int numWords) {
        this.numWords = numWords;
    }

    public int getNumWords(){
        return numWords;
    }
    public void put(K key, V value){
        int hash = Math.abs(hashCode(key) % size);
        Entry<K, V> e = table[hash];

        if (e == null)
        {
            table[hash] = new Entry<K, V>(key, value);
        }
        else
        {
            //Checks if Null is coming up
            while(e.next != null)
            {
                if (e.getKey().equals(key))
                {
                    e.setValue(value);
                    return;
                }
                e = e.next;
            }

            if (e.getKey().equals(key))
            {
                e.setValue(value);
                return;
            }

            e.next = new Entry<K, V>(key, value);
        }
    }

    public V get(K key)
    {
        int hash = Math.abs(hashCode(key)) % size;
        Entry<K, V> e = table[hash];

        if (e == null){
            return null;
        }
        while (e != null){
            if (e.getKey().equals(key))
            {
                return e.getValue();
            }
            e = e.next;
        }

        //If it gets to this point it means we never found the key
        return  null;
    }

    public Entry<K, V> remove(K key)
    {
        int hash = hashCode(key) % size;
        Entry<K, V> e = table[hash];

        if(e == null){
            return null;
        }

        if(e.getKey() == key)
        {
            //Removing From Head of List
            table[hash] = e.next;
            e.next = null;
            return e;
        }

        Entry<K, V> prev = e;
        e = e.next;

        while(e != null)
        {
            if(e.getKey() == key)
            {
                prev.next = e.next;
                e.next = null;
                return e;
            }

            prev = e;
            e = e.next;
        }

        return null;
    }

    public int hashCode(K key) {
        int hash = 17;
        String keyString = key.toString();
        for (int i = 0; i < keyString.length(); i++) {
            hash = 31 * hash + keyString.charAt(i);
        }
        return hash;
    }

    //Returns a list of all the entries
    public List<Entry<K, V>> entries() {
        List<Entry<K, V>> allEntries = new ArrayList<>();
        for (Entry<K, V> entry : table) {
            Entry<K, V> current = entry;
            while (current != null) {
                allEntries.add(current);
                current = current.next;
            }
        }
        return allEntries;
    }

    public ArrayList<String> getAllKeysAsString() {
        ArrayList<String> allKeys = new ArrayList<>();
        for (Entry<K, V> entry : table) {
            Entry<K, V> current = entry;
            while (current != null) {
                allKeys.add(current.getKey().toString());
                current = current.next;
            }
        }
        return allKeys;
    }

    // Provide a method to serialize the table within the MyHashMap2 class
    public void serializeTable(String name, String FilePath) throws IOException {
        String fileName = FilePath + name + ".csv";
        try (FileWriter fileWriter = new FileWriter(fileName)) {
            //writing the data to csv
            for (Entry<K, V> entry : this.entries()) {
                String key = entry.getKey().toString();
                String value = entry.getValue().toString();
                String csvLine = key + "," + value;
                fileWriter.append(csvLine).append("\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
