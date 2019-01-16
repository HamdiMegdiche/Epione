import { User } from './user';

export interface Appointment {
  id: number;
  message: string;
  createdate?: any;
  description?: string;
  date: string;
  startTime: string;
  endTime: string;
  status: string;
  medicalRecord?: any;
  patient: User;
  doctor: User;
  motive?: any;
  evaluation?: any;
}
