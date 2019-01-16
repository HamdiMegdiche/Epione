namespace Epione.Data
{
    using System;
    using System.Data.Entity;
    using System.ComponentModel.DataAnnotations.Schema;
    using System.Linq;
    using Epione.Domain;

    public partial class EpioneContext : DbContext
    {
        public EpioneContext()
            : base("name=EpioneContext")
        {
        }

        public virtual DbSet<appointment> appointments { get; set; }
        public virtual DbSet<complaint> complaints { get; set; }
        public virtual DbSet<discussion> conversations { get; set; }
        public virtual DbSet<evaluation> evaluations { get; set; }
        public virtual DbSet<holiday> holidays { get; set; }
        public virtual DbSet<medicalrecord> medicalrecords { get; set; }
        public virtual DbSet<message> messages { get; set; }
        public virtual DbSet<motive> motives { get; set; }
        public virtual DbSet<recommendation> recommendations { get; set; }
        public virtual DbSet<schedule> schedules { get; set; }
        public virtual DbSet<specialty> specialties { get; set; }
        public virtual DbSet<treatement> treatements { get; set; }
        public virtual DbSet<user> users { get; set; }
        public virtual DbSet<worktime> worktimes { get; set; }

        protected override void OnModelCreating(DbModelBuilder modelBuilder)
        {
            modelBuilder.Entity<appointment>()
                .Property(e => e.message)
                .IsUnicode(false);

            modelBuilder.Entity<appointment>()
                .Property(e => e.status)
                .IsUnicode(false);

            modelBuilder.Entity<appointment>()
                .HasMany(e => e.evaluations)
                .WithOptional(e => e.appointment)
                .HasForeignKey(e => e.appointment_id);

            modelBuilder.Entity<complaint>()
                .Property(e => e.description)
                .IsUnicode(false);

            modelBuilder.Entity<discussion>()
                .HasMany(e => e.messages)
                .WithOptional(e => e.discussion)
                .HasForeignKey(e => e.discussion_id)
                .WillCascadeOnDelete();

            modelBuilder.Entity<evaluation>()
                .Property(e => e.message)
                .IsUnicode(false);

            modelBuilder.Entity<medicalrecord>()
                .HasMany(e => e.appointments)
                .WithOptional(e => e.medicalrecord)
                .HasForeignKey(e => e.medicalRecord_id);

            modelBuilder.Entity<medicalrecord>()
                .HasMany(e => e.treatements)
                .WithOptional(e => e.medicalrecord)
                .HasForeignKey(e => e.medicalRecord_id);

            modelBuilder.Entity<message>()
                .Property(e => e.content)
                .IsUnicode(false);

            modelBuilder.Entity<motive>()
                .Property(e => e.name)
                .IsUnicode(false);

            modelBuilder.Entity<motive>()
                .HasMany(e => e.appointments)
                .WithOptional(e => e.motive)
                .HasForeignKey(e => e.motive_id);

            modelBuilder.Entity<motive>()
                .HasMany(e => e.users)
                .WithMany(e => e.motives)
                .Map(m => m.ToTable("doctor_motive", "epione").MapRightKey("doctor_id"));

            modelBuilder.Entity<schedule>()
                .HasMany(e => e.worktimes)
                .WithOptional(e => e.schedule)
                .HasForeignKey(e => e.schedule_id);

            modelBuilder.Entity<specialty>()
                .Property(e => e.name)
                .IsUnicode(false);

            modelBuilder.Entity<specialty>()
                .HasMany(e => e.motives)
                .WithOptional(e => e.specialty)
                .HasForeignKey(e => e.speciality_id);

            modelBuilder.Entity<specialty>()
                .HasMany(e => e.users)
                .WithOptional(e => e.specialty)
                .HasForeignKey(e => e.specialty_id)
                .WillCascadeOnDelete();

            modelBuilder.Entity<treatement>()
                .Property(e => e.description)
                .IsUnicode(false);

            modelBuilder.Entity<treatement>()
                .Property(e => e.illness)
                .IsUnicode(false);

            modelBuilder.Entity<treatement>()
                .Property(e => e.justif)
                .IsUnicode(false);

            modelBuilder.Entity<treatement>()
                .Property(e => e.result)
                .IsUnicode(false);

            modelBuilder.Entity<treatement>()
                .HasMany(e => e.recommendations)
                .WithOptional(e => e.treatement)
                .HasForeignKey(e => e.treat_id);

            modelBuilder.Entity<user>()
                .Property(e => e.user_type)
                .IsUnicode(false);

            modelBuilder.Entity<user>()
                .Property(e => e.address)
                .IsUnicode(false);

            modelBuilder.Entity<user>()
                .Property(e => e.civility)
                .IsUnicode(false);

            modelBuilder.Entity<user>()
                .Property(e => e.email)
                .IsUnicode(false);

            modelBuilder.Entity<user>()
                .Property(e => e.password)
                .IsUnicode(false);

            modelBuilder.Entity<user>()
                .Property(e => e.phoneNumber)
                .IsUnicode(false);

            modelBuilder.Entity<user>()
                .Property(e => e.photo)
                .IsUnicode(false);

            modelBuilder.Entity<user>()
                .Property(e => e.role)
                .IsUnicode(false);

            modelBuilder.Entity<user>()
                .Property(e => e.status)
                .IsUnicode(false);

            modelBuilder.Entity<user>()
                .Property(e => e.token)
                .IsUnicode(false);

            modelBuilder.Entity<user>()
                .Property(e => e.username)
                .IsUnicode(false);

            modelBuilder.Entity<user>()
                .Property(e => e.licenseNumber)
                .IsUnicode(false);

            modelBuilder.Entity<user>()
                .Property(e => e.medicalfile)
                .IsUnicode(false);

            modelBuilder.Entity<user>()
                .Property(e => e.socialSecurityNumber)
                .IsUnicode(false);

            modelBuilder.Entity<user>()
                .HasMany(e => e.appointments)
                .WithOptional(e => e.user)
                .HasForeignKey(e => e.patient_id);

            modelBuilder.Entity<user>()
                .HasMany(e => e.appointments1)
                .WithOptional(e => e.user1)
                .HasForeignKey(e => e.doctor_id);

            modelBuilder.Entity<user>()
                .HasMany(e => e.complaints)
                .WithOptional(e => e.user)
                .HasForeignKey(e => e.patient_id);

            modelBuilder.Entity<user>()
                .HasMany(e => e.disscussions)
                .WithOptional(e => e.doctor)
                .HasForeignKey(e => e.doctor_id)
                .WillCascadeOnDelete();

            modelBuilder.Entity<user>()
                .HasMany(e => e.disscussions1)
                .WithOptional(e => e.patient)
                .HasForeignKey(e => e.patient_id)
                .WillCascadeOnDelete();

            modelBuilder.Entity<user>()
                .HasMany(e => e.evaluations)
                .WithOptional(e => e.user)
                .HasForeignKey(e => e.patient_id);

            modelBuilder.Entity<user>()
                .HasMany(e => e.holidays)
                .WithOptional(e => e.user)
                .HasForeignKey(e => e.doctor_id);

            modelBuilder.Entity<user>()
                .HasMany(e => e.medicalrecords)
                .WithOptional(e => e.user)
                .HasForeignKey(e => e.patient_id);

            modelBuilder.Entity<user>()
                .HasMany(e => e.schedules)
                .WithOptional(e => e.user)
                .HasForeignKey(e => e.doctor_id)
                .WillCascadeOnDelete();

            modelBuilder.Entity<user>()
                .HasMany(e => e.treatements)
                .WithOptional(e => e.user)
                .HasForeignKey(e => e.doctor_id);

            modelBuilder.Entity<user>()
                .HasMany(e => e.recommendations)
                .WithMany(e => e.users)
                .Map(m => m.ToTable("recommendation_user").MapLeftKey("recomendeddoctors_id").MapRightKey("Recommendation_id"));

            modelBuilder.Entity<user>()
                .HasMany(e => e.medicalrecords1)
                .WithMany(e => e.users)
                .Map(m => m.ToTable("user_medicalrecord").MapLeftKey("Doctor_id").MapRightKey("medicalRecords_id"));

            modelBuilder.Entity<worktime>()
                .Property(e => e.day)
                .IsUnicode(false);
        }
    }
}
