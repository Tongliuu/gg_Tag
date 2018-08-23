// https://leetcode.com/problems/lru-cache/description/

class LRUCache {
    private class DEListNode{
        DEListNode prev,next;
        int key,val;
        DEListNode(int key,int val){
            this.key=key;
            this.val=val;
        }
    }
    
    private HashMap<Integer,DEListNode> map;
    private DEListNode dummyStart,dummyEnd;
    private int capacity;
    
    public LRUCache(int capacity) {
        map=new HashMap<>();
        this.capacity=capacity;
        dummyStart=new DEListNode(-1,-1);
        dummyEnd=new DEListNode(-1,-1);
        dummyStart.next=dummyEnd;
        dummyEnd.prev=dummyStart;
    }
    
    public int get(int key) {
        if(!map.containsKey(key))
            return -1;
        DEListNode node=map.get(key);
        node.next.prev=node.prev;
        node.prev.next=node.next;
        moveToFront(node);
        return node.val;
    }
    
    public void put(int key, int value) {
        if(map.containsKey(key)){
            DEListNode node=map.get(key);
            node.val=value;
            node.next.prev=node.prev;
            node.prev.next=node.next;
            moveToFront(node);
        }else{
            DEListNode node=new DEListNode(key,value);
            if(map.size()>=capacity){
                removeEnd();
            }
            moveToFront(node);
            map.put(key,node);
        }
    }
    
    public void moveToFront(DEListNode node){
        DEListNode temp=dummyStart.next;
        node.next=dummyStart.next;
        node.prev=dummyStart;
        dummyStart.next=node;
        temp.prev=node;
    }
    
    public void removeEnd(){
        DEListNode node=dummyEnd.prev;
        node.prev.next=dummyEnd;
        dummyEnd.prev=node.prev;
        node.next=null;
        node.prev=null;
        map.remove(node.key);
    }
}

/**
 * Your LRUCache object will be instantiated and called as such:
 * LRUCache obj = new LRUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */