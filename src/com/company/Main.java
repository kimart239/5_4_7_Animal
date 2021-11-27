package com.company;
import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Animal[] animals = new Animal[10];
        for (int i = 0; i < animals.length; i++  ) {
            animals[i] = new Animal("This is animal #" + i);
        }

        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        try (ObjectOutputStream stream = new ObjectOutputStream(bos)) {
            stream.writeInt(animals.length );
            for (int i = 0; i < animals.length; i++ ) {
                stream.writeObject(animals[i]);
            }
        } catch (IOException e) {
            System.out.println("Exception was created during writing some animal");
        } finally {
            bos.close();
        }

        byte[] data = bos.toByteArray();
        Animal[] deSerAnimals = deserializeAnimalArray(data);

        //изучаем полученный массив
    }
    public static Animal[] deserializeAnimalArray(byte[] data) throws IOException, ClassNotFoundException {
        Animal[] animalsDes = null;
        try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data))) {
            int count = ois.readInt();
            animalsDes = new Animal[count];

            for (int i = 0; i < count; i++) {
                animalsDes[i] = (Animal) ois.readObject();
            }
        } catch (NotSerializableException e) {
            throw new IllegalArgumentException();
        } catch (ClassCastException e) {
            throw new IllegalArgumentException();
        } catch (IOException e) {
            throw new IllegalArgumentException();
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException();
        } catch (RuntimeException e) {
            throw new IllegalArgumentException();
        }
        return animalsDes;
    }
}
