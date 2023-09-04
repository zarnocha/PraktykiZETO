/* tslint:disable */
/* eslint-disable */
// Generated using typescript-generator version 3.2.1263 on 2023-09-04 16:02:20.

declare namespace api {
  interface CarEntity {
    id: number;
    brand: string;
    model: string;
    productionYear: number;
    fuelConsumption: number;
    engineCapacity: number;
    horsePower: number;
    drive: Drive;
    price: number;
    licensePlate: string;
    seats: number;
    description: string;
    value: number;
    mileage: number;
    available: boolean;
    gearbox: Gearbox;
    picture: string;
    added_by: UserEntity;
  }

  interface RentEntity {
    id: number;
    startTime: Date;
    endTime: Date;
    actualStartTime: Date;
    actualEndTime: Date;
    price: number;
    car: CarEntity;
    user: UserEntity;
  }

  interface UserEntity {
    id: number;
    login: string;
    password: string;
    firstName: string;
    lastName: string;
    creditCardNuber: string;
    creditCardExpDate: Date;
    cvv: string;
    isAdmin: boolean;
    rents: RentEntity[];
    cars: CarEntity[];
  }

  type Drive = 'FWD' | 'RWD' | 'AWD';

  type Gearbox = 'MANUAL' | 'AUTOMATIC' | 'SEMI_AUTOMATIC';
}
