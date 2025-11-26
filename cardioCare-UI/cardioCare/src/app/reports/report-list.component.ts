import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReportService } from './report.service';
import { Report } from '../models/report';

@Component({
  selector: 'app-report',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './report-list.component.html'
})
export class ReportComponent implements OnInit {
  reports: Report[] = [];
  loading = true;
  errorMessage = '';

  constructor(private reportService: ReportService) {}

  ngOnInit(): void {
    this.reportService.getAll().subscribe({
      next: (data) => {
        this.reports = data;
        this.loading = false;
      },
      error: (err) => {
        console.error(err);
        this.errorMessage = 'Error al cargar los pacientes';
        this.loading = false;
      }
    });
  }
}
