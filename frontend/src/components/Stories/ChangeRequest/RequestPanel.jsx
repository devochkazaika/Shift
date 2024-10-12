import React from 'react';
import style from './Request.module.scss'
import Button from '../../ui/Button';
import { rollBackOperation } from '../../../api/history';

const RequestPanel = ({ data }) => {
    const handleOnSubmit = async (id) => {
        try{
            const response = rollBackOperation(id);
            console.log(response);

        }
        catch (exc){
            console.log("Could not get response")
        }
      };
    return (
        <li className="listFrame">
            <details>
              <summary draggable="true">
                <div>
                    <table>
                        <tr className={style.tr}>
                            <td>{data["bankId"]}</td>
                            <td>{data["platform"]}</td> 
                            <td>{data["bankId"]}</td> 
                            <td>{data["operationType"]}</td>
                            {data["rollBackAble"] ? <td>
                                <Button
                                text="Отозвать"
                                type="button"
                                color="red"
                                handleOnClick={() => handleOnSubmit(data["id"])}
                                />
                            </td> : <></>}
                        </tr>
                
                    </table>
                </div>
              </summary>
            </details>
        </li>
    )
}

export default RequestPanel