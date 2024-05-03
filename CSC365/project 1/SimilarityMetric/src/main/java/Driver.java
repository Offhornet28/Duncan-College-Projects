public class Driver {

    public static void main(String[] args){
        MyHashMap<String, Integer> myHashMap = new MyHashMap<String, Integer>();

        myHashMap.put("A", 1);
        myHashMap.put("E", 2);
        myHashMap.put("C", 3);
        myHashMap.put("H", 4);
        myHashMap.put("1", 5);
        myHashMap.put("2", 6);
        myHashMap.put("3", 7);
        myHashMap.put("4", 36868);

        System.out.println(myHashMap.get("Hello"));
        System.out.println(myHashMap.get("4"));
        System.out.println(myHashMap.remove("A"));
        System.out.println(myHashMap.get("A"));
    }
}
