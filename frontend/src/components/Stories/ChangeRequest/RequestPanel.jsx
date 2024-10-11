import React from 'react';
import style from './Request.module.scss'
import Button from '../../ui/Button';

const RequestPanel = ({ data }) => {
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
                            <td>
                                <Button
                                text="Отозвать"
                                type="button"
                                color="red"
                                // handleOnClick={() => handleOnSubmit(story, platform)}
                                />
                            </td>  
                        </tr>
                
                    </table>
                </div>
              </summary>
            </details>
        </li>
    )
}

export default RequestPanel