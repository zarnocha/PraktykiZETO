/* tslint:disable */
/* eslint-disable */
// Generated using typescript-generator version 3.2.1263 on 2023-09-06 08:03:47.

declare namespace api {
  interface CarListDTO {
    id: number;
    description: string;
    brand: string;
    model: string;
    productionYear: number;
    fuelConsumption: number;
    engineCapacity: number;
    drive: Drive;
    seats: number;
    gearbox: Gearbox;
    wholePrice: number;
    picture: string;
  }

  interface CarListQueryParamsDTO {
    brand?: string;
    model?: string;
    horsePowerFrom?: number;
    horsePowerTo?: number;
    gearbox?: Gearbox;
    drive?: Drive;
    from?: Date;
    to?: Date;
    available?: boolean;
  }

  interface SingleCarDTO {}

  type Drive = 'FWD' | 'RWD' | 'AWD';

  type Gearbox = 'MANUAL' | 'AUTOMATIC' | 'SEMI_AUTOMATIC';
}
