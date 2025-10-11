import { gql } from 'apollo-angular';

export const GET_QMS_TO_DOI_TRA_INFO_BY_LOT_NUMBER = gql`
  query QmsToDoiTraInfoByLotNumber($lotNumber: String!) {
    qmsToDoiTraInfoByLotNumber(lotNumber: $lotNumber) {
      pqcScan100Pass {
        id
        qr
        material
        createdAt
        machine
        feeder
        user_check
      }
      pqcScan100Fail {
        id
        qr
        material
        createdAt
        machine
        feeder
        user_check
      }
      pqcCheckNVL {
        id
        CheckPerson
        conclude
      }
      pqcCheckTestNVL {
        id
        itemName
        itemCode
        partNumber
        sampleQuantity
        checkDate
        regulationCheck
        allowResult
        realResult
        returnDay
        vendor
        lot
        pqcDrawNvlId
      }
      pqcBomErrorDetail {
        id
        quantity
        pqcBomQuantityId
        pqcWorkOrderId
        pqcBomWorkOrderId
      }
      pqcBomQuantity {
        id
        quantity
        pqcWorkOrderId
        pqcBomWorkOrderId
      }
      pqcBomWorkOrder {
        id
        bomQuantity
        itemCode
        itemName
        partNumber
        quantity
        vendor
        version
        workOrderId
        workOrderQuantity
      }
    }
  }
`;