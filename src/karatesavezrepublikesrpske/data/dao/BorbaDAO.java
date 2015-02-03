/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package karatesavezrepublikesrpske.data.dao;

import java.util.List;
import karatesavezrepublikesrpske.data.dto.BorbaDTO;

/**
 *
 * @author Marko
 */
public interface BorbaDAO {

    public List<BorbaDTO> borbe(int idTakmicenja);

    public List<BorbaDTO> borbe(int idTakmicenja, int idKategorije);

    public List<BorbaDTO> borbe(int idTakmicenja, int idKategorije, long jmb);

    public List<BorbaDTO> borbe(long jmb);

    public boolean dodajBorbu(BorbaDTO borba);
}
