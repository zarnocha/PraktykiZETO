/* tslint:disable */
/* eslint-disable */
// Generated using typescript-generator version 3.2.1263 on 2023-09-07 15:02:25.

declare namespace api {
  interface CarBrandModelDTO {
    brand: string;
    models: string[];
  }

  interface CarFilterDTO {
    minHorsepower?: number;
    maxHorsepower?: number;
    brands?: CarBrandModelDTO[];
  }

  interface CarListDTO {
    id: number;
    brand: string;
    model: string;
    productionYear: number;
    fuelConsumption: number;
    engineCapacity: number;
    drive: Drive;
    seats: number;
    gearbox: Gearbox;
    wholePrice: number;
    description: string;
    picture: string;
  }

  interface CarBrandModelDTO {
    brand: string;
    models: string[];
  }

  interface CarFilterDTO {
    minHorsepower?: number;
    maxHorsepower?: number;
    brands?: CarBrandModelDTO[];
  }

  interface CarListDTO {
    id: number;
    brand: string;
    model: string;
    productionYear: number;
    fuelConsumption: number;
    engineCapacity: number;
    drive: Drive;
    seats: number;
    gearbox: Gearbox;
    wholePrice: number;
    description: string;
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
