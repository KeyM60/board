package BoardSystem.interfaceIO;

import BoardSystem.interfaceIO.crud.DeleteALLBoard;
import BoardSystem.interfaceIO.crud.DeleteDB;
import BoardSystem.interfaceIO.crud.InsertDB;
import BoardSystem.interfaceIO.crud.ReadALLBoard;
import BoardSystem.interfaceIO.crud.ReadDB;
import BoardSystem.interfaceIO.crud.UpdateDB;

public interface BoardIO extends InsertDB,ReadDB, UpdateDB, DeleteDB, ReadALLBoard, DeleteALLBoard {

}
