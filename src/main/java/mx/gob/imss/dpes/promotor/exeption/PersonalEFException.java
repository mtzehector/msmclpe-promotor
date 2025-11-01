/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.promotor.exeption;

/**
 *
 * @author cesar.romero
 */
import mx.gob.imss.dpes.common.exception.BusinessException;

public class PersonalEFException extends BusinessException {
  private static final String KEY = "msg005";

  public PersonalEFException() {super(KEY);}
}