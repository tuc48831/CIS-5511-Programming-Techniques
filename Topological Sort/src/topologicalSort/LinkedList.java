package topologicalSort;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * @author tuc48831
 * I WROTE THISE CODE FOR A 2168 CLASS
 * I AM JUST RE-USING IT
 * I WILL MAKE SURE TO RE-FAMILIARIZE MYSELF/COMMENT IT RELEVANT TO THIS ASSIGNMENT
 */
public class LinkedList<E> { //this is my code for a linked list

    Node<E> head;   //i used my node class to include a head
    Node<E> tail;   //and a tail
    int size;       //and as i add and remove items i keep track of the size so I don't have to iterate through the whole list at O(n) to figure it out

    public LinkedList() { //initializer with no argument to just set everything to null/0
        head = null;
        tail = null;
        size = 0;
    }
    
    public int getSize(){ //return the size of the linked list
        return this.size;
    }
    
    public boolean add(E e) { //overloading add to just place a node at the end when no index is given
        return(add(this.size, e));
    }

    public boolean addNoDupes(E e){ //this is a special funtion I made to trim down the size of the successor array in case anyone added an edge multiple times
        if(this.size == 0){ //insert into empty linked list, this is O(c)
            add(e);
        }else{ //else iterate through list
            Node<E> current = head;
            while(current != null){ //until i reach the end
                if(current.getElement() == e){//if the element is already in the list, break
                    break;
                }else{//else iterate
                    current = current.getNext();
                }
            }
            if(current == null){ //if i reached the end of the list, it was not already there
                add(e);  //add it to the list at the end
                return true;
            }
        }
        return false; //return that item wasn't added to list
    }
    
    public boolean add(int index, E e) { //add a node at the specified index, handles special cases
        
        if((index < 0)||(index > this.size)){
            return false; //return false for out of bounds indices
        }
        
        Node<E> temp = new Node<E>(e);
        
        if(this.size == 0){ //inset into empty linked list, this is O(c)
            head = temp;
            tail = temp;
            temp.setNext(null);
            temp.setPrev(null);
        }else if(index == 0){ //insert as head, this is O(c) since I just use the head reference directly rather than iterate
            temp.setNext(head);
            head.setPrev(temp);
            head = temp;
            head.setPrev(null);
        }else if(index == size){ //insert as tail, this is O(c) since I don't iterate through and just use the tail as the reference
            tail.setNext(temp);
            temp.setPrev(tail);
            tail = tail.getNext();
            tail.setNext(null);
        }else{ //else insert in middle of list, this is O(n) but I won't use this one in my topSorts
            Node<E> reference = getNode(index);
            temp.setNext(reference);
            temp.setPrev(reference.getPrev());
            reference.getPrev().setNext(temp);
            reference.setPrev(temp);
        }
        
        size++; //increment size
        return true; //return true that operation was successful
    }

    private Node<E> getNode(int index) { //this is leftover from my previous creation of a linked list class
        if((index < 0) || (index > size)){  //this is O(n) but I never use it
            return null;
        }else{
            Node<E> current = head;
            for(int i = 0; i < index; i++){
                current = current.getNext();
            }
            return current;
        }
    }

    public E removeHead(){ //so I can remove the head, it calls my remove function using index 0
        return(remove(0)); //the remove function references the head node directly and there is no iteration, meaning it should be O(c)
    }
    
    public E removeTail(){ //so I can remove the head, it calls my remove function using index this.size
        return(remove(this.size)); //the remove function references the tail node directly and there is no iteration, meaning it should be O(c)
    }
    
    public E remove(int index) { //removes a node at a given index
        if((index < 0) || (index > size)){ //handle out of bounds cases
            return null;
        }
        
        E toRemove = null;
        
        if(this.size == 1){ //if there is only one item
            toRemove = head.getElement();
            head = null;
            tail = null;
        }else if(index == 0){ //to remove the head doesn't involve iterating, so it should be O(c)
            toRemove = head.getElement();
            head = head.getNext();
        }else if(index == size){ //to remove the tail doesn't involve iterating, so it should be O(c)
            toRemove = tail.getElement();
            tail = tail.getPrev();
            tail.setNext(null);
        }else{ //else remove by index number
            toRemove = getNode(index).getElement();
            getNode(index-1).setNext(getNode(index).getNext());
        }
        
        size--; //decrement the size
        return toRemove;
    }

    public String toString() {  //a to string function that iterates through my list and prints it, since this is a "reporting" feature i don't count it
        Node<E> current = head;
        StringBuilder result = new StringBuilder();
        if (size == 0) {
            return "";
        }
        if (size == 1) {
            return head.getElement().toString();

        } else {
            do {
                result.append(current.getElement());
                if(current.getNext() != null){
                    result.append(", ");
                }
                current = current.getNext();
            } while (current != null);
        }
        return result.toString();
    }
}