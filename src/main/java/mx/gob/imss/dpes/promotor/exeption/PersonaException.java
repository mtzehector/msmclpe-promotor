/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.promotor.exeption;

/**
 *
 * @author osiris.hernandez
 */
import mx.gob.imss.dpes.common.exception.BusinessException;

public class PersonaException extends BusinessException {
  private static final String KEY = "msg001";

  public PersonaException() {super(KEY);}
}