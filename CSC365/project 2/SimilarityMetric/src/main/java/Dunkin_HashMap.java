import java.io.FileWriter;
import java.io.IOException;

public class Dunkin_HashMap<K, V>
{
    private class Entry<K, V>{
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

    private final int SIZE = 13337;

    private Entry<K, V> table[];

    public Dunkin_HashMap(){
        table = new Entry[SIZE];
    }

    public void put(K key, V value){
        int hash = Math.abs(hashCode(key) % SIZE);
        Entry<K, V> e = table[hash];

        if (e == null)
        {
            table[hash] = new Entry<K, V>(key, value);
        }
        else
        {
            //Checks if Null is coming up
            while(e.next != null){
                if (e.getKey() == key)
                {
                    e.setValue(value);
                    return;
                }
                e = e.next;
            }
            if (e.getKey() == key)
            {
                e.setValue(value);
                return;
            }

            e.next = new Entry<K, V>(key, value);
        }
    }

    public V get(K key)
    {
        int hash = Math.abs(hashCode(key)) % SIZE;
        System.out.println(hash);
        Entry<K, V> e = table[hash];

        if (e == null){
            return null;
        }
        while (e != null){
            if (e.getKey() == key)
            {
                return e.getValue();
            }
            e = e.next;
        }

        //If it gets to this point it means we never found the key
        return  null;
    }

    public Entry<K, V> remove(K key) {
        int hash = hashCode(key) % SIZE;
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

    //hash table csv serializer
    public String serializeTable(String name, Dunkin_HashMap<K, V> map) throws IOException {
        String fileName = "src/main/SerializedBusinesses/" + name;
        FileWriter fileWriter = new FileWriter(fileName);
        //writing the csv header
        String header = "key,value";
        fileWriter.append(header);
        fileWriter.append("\n");
        //writing the data to csv
        for (Entry<K, V> entry : map.table) {
            String key = (entry.getKey()).toString();
            String value = (entry.getValue()).toString();
            String csvLine = key + "," + value;
            fileWriter.append(csvLine);
            fileWriter.append("\n");
        }
        //closing the file
        fileWriter.close();
        //return the name of the file
        return fileName;
    }
}
