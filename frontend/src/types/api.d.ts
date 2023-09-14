/* tslint:disable */
/* eslint-disable */
// Generated using typescript-generator version 3.2.1263 on 2023-09-13 08:09:22.

declare namespace api {

    interface CarBrandModelDTO {
        brand: string;
        models: string[];
    }

    interface CarDTO {
        id: number;
        brand: string;
        model: string;
        productionYear: number;
        fuelConsumption: number;
        engineCapacity: number;
        drive: Drive;
        seats: number;
        gearbox: Gearbox;
        dayPrice: number;
        description: string;
        picture: string;
    }

    interface CarFilterDTO {
        minHorsepower?: number;
        maxHorsepower?: number;
        brands?: CarBrandModelDTO[];
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

    interface AddRentDTO {
        startTime: Date;
        endTime: Date;
        carId: number;
        userId: number;
    }

    interface RentCarDTO {
        id: number;
        brand: string;
        model: string;
        picture: string;
    }

    interface RentDTO {
        id: number;
        startTime: Date;
        endTime: Date;
        actualStartTime: Date;
        actualEndTime: Date;
        price: number;
        car: RentCarDTO;
        user: SimpleUserDTO;
    }

    interface AdminRegisterDTO {
        login: string;
        password: string;
        firstName: string;
        lastName: string;
    }

    interface SimpleUserDTO {
        id: number;
        firstName: string;
        lastName: string;
    }

    interface UserLoginDTO {
        login: string;
        password: string;
    }

    interface UserRegisterDTO {
        login: string;
        password: string;
        firstName: string;
        lastName: string;
        creditCardNuber: string;
        creditCardExpDate: Date;
        cvv: string;
    }

    type Drive = "FWD" | "RWD" | "AWD";

    type Gearbox = "MANUAL" | "AUTOMATIC" | "SEMI_AUTOMATIC";

}
