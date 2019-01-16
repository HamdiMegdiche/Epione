export class Schedule {
    constructor(public id: number,
    public slot: number) { }
}

export class User {
    constructor() {
        this.id  = null;
        this.username  = null;
        this.firstName  = null;
        this.lastName  = null;
        this.sexe  = null;
        this.email  = null;
        this.password  = null;
        this.photo  = null;
        this.phoneNumber  = null;
        this.address  = null;
        this.civility  = null;
        this.creationDate  = null;
        this.lastLogin  = null;
        this.token  = null;
        this.status  = null;
        this.role  = null;
        this.licenseNumber  = null;
        this.socialSecurityNumber  = null;
        this.description  = null;
        this.longitude  = null;
        this.latitude  = null;
        this.specialty  = null;
        this.medicalfile  = null;
        this.schedule  = null;

    }

    public id: number;
    public username: string;
    public firstName: string;
    public lastName: string;
    public sexe?: string;
    public email: string;
    public password: string;
    public photo: string;
    public phoneNumber?: string;
    public address?: any;
    public civility?: string;
    public creationDate: string;
    public lastLogin: string;
    public token: string;
    public status: string;
    public role: string;
    public licenseNumber?: string;
    public socialSecurityNumber?: string;
    public description?: any;
    public longitude?: any;
    public latitude?: any;
    public specialty?: any;
    public medicalfile?: any;
    public schedule?: Schedule[];
}

