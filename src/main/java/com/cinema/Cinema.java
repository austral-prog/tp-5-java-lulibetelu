package com.cinema;

import java.util.ArrayList;

/**
 * Clase que representa una sala de cine.
 */
public class Cinema {

    private Seat[][] seats;
    // crea una variable 'seats' que usa multiples instancias de Seat

    /**
     * Construye una sala de cine. Se le pasa como dato un arreglo cuyo tamaño
     * es la cantidad de filas y los enteros que tiene son el número de butacas en cada fila.
     */
    public Cinema(int[] rows) {
        seats = new Seat[rows.length][];
        initSeats(rows);
    }

    /**
     * Inicializa las butacas de la sala de cine.
     *
     * @param rows arreglo que contiene la cantidad de butacas en cada fila
     */
    private void initSeats(int[] rows) {
        for (int i = 0; i < rows.length; i++) {
            seats[i] = new Seat[rows[i]];      // crea el espacio para las columnas de cada fila
        }
        for (int i = 0; i < seats.length; i++) {
            for (int j = 0; j < seats[i].length; j++) {
                seats[i][j] = new Seat(i, j);   // crea las columnas de cada fila
            }
        }
    }

    /**
     * Cuenta la cantidad de seats disponibles en el cine.
     */
    public int countAvailableSeats() {
        int count = 0;
        for (int i = 0; i < seats.length; i++){
            for (int j = 0; j < seats[i].length; j++){
                Seat seat = seats[i][j];
        // no se llama al objeto usando new Seat(i, j) por que quiero acceder a los valores de una instancia que ya fue creada en el constructor, por lo que no debe ser creada de nuevo
        //seat es una variable que referencia a una instancia existente de Seat, no que crea una nueva.
                if (seat.isAvailable()){
                    count ++;
                }
            }
        }
        return count;
    }

    /**
     * Busca la primera butaca libre dentro de una fila o null si no encuentra.
     */
    public Seat findFirstAvailableSeatInRow(int row) {
        if (row < seats.length) {
            for (int i = 0; i < seats[row].length; i++) {
                Seat seat = seats[row][i];
                if (seat.isAvailable()) {
                    return seat;
                }
            }
        }
        return null;
    }

    /**
     * Busca la primera butaca libre o null si no encuentra.
     */
    public Seat findFirstAvailableSeat() {
        for (int i = 0; i < seats.length; i++){
            for (int j = 0; j < seats[i].length; j++){
                Seat seat = seats[i][j];
                if (seat.isAvailable()){
                    return seat;
                }
            }
        }
        return null;
    }

    /**
     * Busca las N butacas libres consecutivas en una fila. Si no hay, retorna null.
     *
     * @param row    fila en la que buscará las butacas.
     * @param amount el número de butacas necesarias (N).
     * @return La primer butaca de la serie de N butacas, si no hay retorna null.
     */
    public Seat getAvailableSeatsInRow(int row, int amount) {
        int count = 0;
        for (int i = 0; i < seats[row].length; i++){
            Seat seat = seats[row][i];
            if (seat.isAvailable()){
                count++;
                if (count == amount){
                    return seats[row][i - amount + 1];
                }
            }
            else{
                count = 0;
            }
        }
        return null;
    }

    /**
     * Busca en toda la sala N butacas libres consecutivas. Si las encuentra
     * retorna la primer butaca de la serie, si no retorna null.
     *
     * @param amount el número de butacas pedidas.
     */
    public Seat getAvailableSeats(int amount) {
        int count = 0;
        for (int i = 0; i < seats.length; i++){
            for (int j = 0; j < seats[i].length; j++) {
                Seat seat = seats[i][j];
                if (seat.isAvailable()){
                    count ++;
                    if (count == amount){
                        return seats[i][j - amount + 1];
                    }
                }
                else{
                    count = 0;
                }
            }
        }
        return null;
    }

    /**
     * Marca como ocupadas la cantidad de butacas empezando por la que se le pasa.
     *
     * @param seat   butaca inicial de la serie.
     * @param amount la cantidad de butacas a reservar.
     */
    public void takeSeats(Seat seat, int amount) {
        int count = 0;
        if (amount <= seats[seat.getRow()].length) {
            for (int i = seat.getSeatNumber(); i < seats[seat.getRow()].length; i++) {
                if (count < amount) {
                    seats[seat.getRow()][i].takeSeat();
                    count++;
                }
            }
        }
        else{
            throw new ArrayIndexOutOfBoundsException("Not enough seats");
        }


    }

    /**
     * Libera la cantidad de butacas consecutivas empezando por la que se le pasa.
     *
     * @param seat   butaca inicial de la serie.
     * @param amount la cantidad de butacas a liberar.
     */
    public void releaseSeats(Seat seat, int amount) {
        int count = 0;
        if (amount <= seats[seat.getRow()].length) {
            for (int i = seat.getSeatNumber(); i < seats[seat.getRow()].length; i++) {
                if (count < amount) {
                    seats[seat.getRow()][i].releaseSeat();
                    count++;
                }
            }
        }
        else{
            throw new ArrayIndexOutOfBoundsException("To many seats");
        }
    }
}